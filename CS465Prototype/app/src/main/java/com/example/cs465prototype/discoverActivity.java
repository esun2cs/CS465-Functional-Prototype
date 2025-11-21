package com.example.cs465prototype;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
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
                View card = inflater.inflate(R.layout.business_card, cardContainer, false);

                ((TextView) card.findViewById(R.id.business_name)).setText(b.name);
                ((TextView) card.findViewById(R.id.business_added)).setText("Added recently");
                ((TextView) card.findViewById(R.id.business_category)).setText(b.category);
                ((TextView) card.findViewById(R.id.business_description)).setText(b.description);

                ImageView img = card.findViewById(R.id.business_photo);
                String imageName = b.photo.toLowerCase().replace(".jpg", "").replace(".jpeg", "").replace(".png", "").replace("-", "_").replace(" ", "_");
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

                ImageView star = card.findViewById(R.id.business_favorite_star);
                updateStarIcon(star, b.favorited);

                star.setOnClickListener(v -> {
                    b.favorited = !b.favorited;
                    updateStarIcon(star, b.favorited);
                });

                cardContainer.addView(card);
            }
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_discover);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(this, MainActivity.class));
                return true;
            } else if (id == R.id.nav_discover) {
                return true;
            } else if (id == R.id.nav_search) {
                startActivity(new Intent(this, searchActivity.class));
                return true;
            } else if (id == R.id.nav_favorites) {
                startActivity(new Intent(this, favoritesActivity.class));
                return true;
            }
            return false;
        });
    }

    private void updateStarIcon(ImageView star, boolean isFav) {
        if (isFav) {
            star.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            star.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }
}
