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
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0); // systemBars.bottom nav bar gap fix
            return insets;
        });

        // Data loading should happen once in onCreate.
        BusinessDataManager dm = BusinessDataManager.getInstance();
        if (dm.allBusinesses.isEmpty()) {
            dm.loadFromJson(this);
        }

        // Navigation setup should also happen once in onCreate.
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

    /**
     * By moving the card-building logic to onResume, the UI will be refreshed
     * every time the activity comes back into view. This fixes the bug where
     * the star status wasn't updated when returning from the profile page.
     */
    @Override
    protected void onResume() {
        super.onResume();

        LinearLayout cardContainer = findViewById(R.id.card_container);
        // Clear old cards to prevent duplicates before re-populating.
        cardContainer.removeAllViews();

        BusinessDataManager dm = BusinessDataManager.getInstance();
        LayoutInflater inflater = LayoutInflater.from(this);

        for (Business b : dm.allBusinesses) {
            if (b.isNew) {

                View card = inflater.inflate(R.layout.business_card, cardContainer, false);

                ((TextView) card.findViewById(R.id.business_name)).setText(b.name);
                ((TextView) card.findViewById(R.id.business_added)).setText("Added recently");
                ((TextView) card.findViewById(R.id.business_category)).setText(b.category);
                ((TextView) card.findViewById(R.id.business_description)).setText(b.description);

                // display new badge visibility status
                TextView newBadge = card.findViewById(R.id.business_new_badge);
                newBadge.setVisibility(b.isNew ? View.VISIBLE : View.GONE);

                ImageView img = card.findViewById(R.id.business_photo);
                String imageName = b.photo.toLowerCase()
                        .replace(".jpg", "")
                        .replace(".jpeg", "")
                        .replace(".png", "")
                        .replace("-", "_")
                        .replace(" ", "_");

                int imgRes = getResources().getIdentifier(imageName, "drawable", getPackageName());
                if (imgRes != 0) {
                    img.setImageResource(imgRes);
                } else {
                    img.setImageResource(R.drawable.ic_launcher_background);
                }

                card.setOnClickListener(v -> {
                    Intent i = new Intent(this, BusinessProfileActivity.class);
                    i.putExtra("business_id", b.id);
                    startActivity(i);
                });

                // Star Icon Logic
                ImageView star = card.findViewById(R.id.business_favorite_star);
                if (b.favorited) {
                    star.setImageResource(android.R.drawable.btn_star_big_on);
                } else {
                    star.setImageResource(android.R.drawable.btn_star_big_off);
                }

                star.setOnClickListener(v -> {
                    b.favorited = !b.favorited;
                    if (b.favorited) {
                        star.setImageResource(android.R.drawable.btn_star_big_on);
                    } else {
                        star.setImageResource(android.R.drawable.btn_star_big_off);
                    }
                });

                cardContainer.addView(card);
            }
        }
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
