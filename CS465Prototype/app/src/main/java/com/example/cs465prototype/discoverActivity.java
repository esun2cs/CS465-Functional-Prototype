package com.example.cs465prototype;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class discoverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_discover);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LinearLayout cardContainer = findViewById(R.id.card_container);
        BusinessDataManager dm = BusinessDataManager.getInstance();
        // Ensure data is loaded
        if (dm.allBusinesses.size() == 0) {
            dm.loadFromJson(this);
        }


        LayoutInflater inflater = LayoutInflater.from(this);

        for (Business b : dm.allBusinesses) {
            if (b.isNew) {

                // Inflate card
                View card = inflater.inflate(R.layout.business_card, cardContainer, false);

                // Set name
                TextView name = card.findViewById(R.id.business_name);
                name.setText(b.name);

                // Example: "Added 12 days ago"
                TextView added = card.findViewById(R.id.business_added);
                added.setText("Added recently");

                // Set category
                TextView category = card.findViewById(R.id.business_category);
                category.setText(b.category);

                // Set description
                TextView desc = card.findViewById(R.id.business_description);
                desc.setText(b.description);

                // Set image (handles PNG/JPG + normalizes the name)
                ImageView img = card.findViewById(R.id.business_photo);

                String imageName = b.photo
                        .toLowerCase()
                        .replace(".jpg", "")
                        .replace(".jpeg", "")
                        .replace(".png", "")
                        .replace("-", "_")
                        .replace(" ", "_");

                int imgRes = getResources().getIdentifier(imageName, "drawable", getPackageName());

                if (imgRes != 0) {
                    img.setImageResource(imgRes);
                } else {
                    img.setImageResource(R.drawable.ic_launcher_background); // TEMP FALLBACK
                }

                // Add click handler
                card.setOnClickListener(v -> {
                    Intent i = new Intent(this, BusinessProfileActivity.class);
                    i.putExtra("business_id", b.id);
                    startActivity(i);
                });
                
                // --- Star Icon Logic (Minimal Change) ---
                ImageView star = card.findViewById(R.id.business_favorite_star);
                // Set initial star state
                if (b.favorited) {
                    star.setImageResource(android.R.drawable.btn_star_big_on);
                } else {
                    star.setImageResource(android.R.drawable.btn_star_big_off);
                }
                // Add click listener to the star to toggle the favorite state
                star.setOnClickListener(v -> {
                    b.favorited = !b.favorited; // Toggle state
                    // Update icon immediately
                    if (b.favorited) {
                        star.setImageResource(android.R.drawable.btn_star_big_on);
                    } else {
                        star.setImageResource(android.R.drawable.btn_star_big_off);
                    }
                });

                // Add card to container
                cardContainer.addView(card);
            }
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    Intent i = new Intent(discoverActivity.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(i);
                    return true;
                } else if (id == R.id.nav_discover) {
                    launchDiscover(null);
                    return true;
                } else if (id == R.id.nav_search) {
                    launchSearch(null);
                    return true;
                } else if (id == R.id.nav_favorites) {
                    launchFavorites(null);
                    return true;
                }
                return false;
            }
        });
    }

    public void launchSearch(View v) {
        Intent i = new Intent(this, searchActivity.class);
        startActivity(i);
    }

    public void launchDiscover(View v) {
        Intent i = new Intent(this, discoverActivity.class);
        startActivity(i);
    }

    public void launchFavorites(View v) {
        Intent i = new Intent(this, favoritesActivity.class);
        startActivity(i);
    }

    public void launchBusinessProfile(View v) {
        Intent i = new Intent(this, BusinessProfileActivity.class);
        startActivity(i);
    }
}
