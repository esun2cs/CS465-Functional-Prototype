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

public class favoritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favorites);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadFavorites();

//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setSelectedItemId(R.id.nav_favorites);
//        bottomNavigationView.setOnItemSelectedListener(this::handleNavigation);
        // Bottom navigation
        BottomNavigationView nav = findViewById(R.id.bottom_navigation);
        nav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                startActivity(new Intent(favoritesActivity.this, MainActivity.class));
                return true;
            } else if (id == R.id.nav_discover) {
                startActivity(new Intent(favoritesActivity.this, discoverActivity.class));
                return true;
            } else if (id == R.id.nav_search) {
                startActivity(new Intent(favoritesActivity.this, searchActivity.class));
                return true;
            } else if (id == R.id.nav_favorites) {
                startActivity(new Intent(favoritesActivity.this, favoritesActivity.class));
                return true;
            }

            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFavorites();  // refresh every time you return
    }

    private boolean handleNavigation(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        } else if (id == R.id.nav_discover) {
            startActivity(new Intent(this, discoverActivity.class));
            return true;
        } else if (id == R.id.nav_search) {
            startActivity(new Intent(this, searchActivity.class));
            return true;
        } else if (id == R.id.nav_favorites) {
            return true;
        }

        return false;
    }

    private void loadFavorites() {
        LinearLayout container = findViewById(R.id.favorites_container);
        container.removeAllViews();

        BusinessDataManager dm = BusinessDataManager.getInstance();
        LayoutInflater inflater = LayoutInflater.from(this);

        for (Business b : dm.allBusinesses) {
            if (!b.favorited) continue;

            View card = inflater.inflate(R.layout.business_card, container, false);

            ((TextView) card.findViewById(R.id.business_name)).setText(b.name);
            ((TextView) card.findViewById(R.id.business_added)).setText("Favorited");
            ((TextView) card.findViewById(R.id.business_category)).setText(b.category);
            ((TextView) card.findViewById(R.id.business_description)).setText(b.description);

//            ImageView img = card.findViewById(R.id.business_photo);
//            int imgRes = getResources().getIdentifier(
//                    b.photo.replace(".jpg",""), "drawable", getPackageName());
//            if (imgRes != 0) img.setImageResource(imgRes);

            ImageView img = card.findViewById(R.id.business_photo);

            String imgName = b.photo
                    .toLowerCase()
                    .replace(".jpg", "")
                    .replace(".jpeg", "")
                    .replace(".png", "")
                    .replace("-", "_")
                    .replace(" ", "_");

            int imgRes = getResources().getIdentifier(imgName, "drawable", getPackageName());

            if (imgRes != 0) {
                img.setImageResource(imgRes);
            }


            card.setOnClickListener(v -> {
                Intent i = new Intent(this, BusinessProfileActivity.class);
                i.putExtra("business_id", b.id);
                startActivity(i);
            });

            container.addView(card);
        }
    }
}
