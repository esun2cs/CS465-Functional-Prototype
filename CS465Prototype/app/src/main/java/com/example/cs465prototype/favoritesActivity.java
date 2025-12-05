package com.example.cs465prototype;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.stream.Collectors;

public class favoritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favorites);

        BusinessDataManager dm = BusinessDataManager.getInstance();
        if (dm.allBusinesses.size()== 0) {
            dm.loadFromJson(this);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0); // systemBars.bottom nav bar gap fix
            return insets;
        });

        BottomNavigationView nav = findViewById(R.id.bottom_navigation);
        nav.setSelectedItemId(R.id.nav_favorites);
        nav.setOnItemSelectedListener(item -> {
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
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFavorites();
    }

    private void loadFavorites() {
        LinearLayout container = findViewById(R.id.favorites_container);
        ScrollView scrollView = findViewById(R.id.favorites_scroll_view);
        TextView emptyStateText = findViewById(R.id.empty_state_text);
        container.removeAllViews();

        BusinessDataManager dm = BusinessDataManager.getInstance();
        List<Business> favoritedBusinesses = dm.allBusinesses.stream()
                .filter(b -> b.favorited)
                .collect(Collectors.toList());

        if (favoritedBusinesses.isEmpty()) {
            scrollView.setVisibility(View.GONE);
            emptyStateText.setVisibility(View.VISIBLE);
        } else {
            scrollView.setVisibility(View.VISIBLE);
            emptyStateText.setVisibility(View.GONE);

            LayoutInflater inflater = LayoutInflater.from(this);
            for (Business b : favoritedBusinesses) {
                View card = inflater.inflate(R.layout.business_card, container, false);

                ((TextView) card.findViewById(R.id.business_name)).setText(b.name);
                ((TextView) card.findViewById(R.id.business_added)).setText("Favorited");
                ((TextView) card.findViewById(R.id.business_category)).setText(b.category);
                ((TextView) card.findViewById(R.id.business_description)).setText(b.description);

                ImageView img = card.findViewById(R.id.business_photo);
                // This logic needs to be robust to handle various image name formats
                String imgName = b.photo.toLowerCase()
                        .replace(".jpg", "")
                        .replace(".jpeg", "")
                        .replace(".png", "")
                        .replace("-", "_")
                        .replace(" ", "_");

                int imgRes = getResources().getIdentifier(imgName, "drawable", getPackageName());
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
                    b.favorited = false;
                    loadFavorites();
                });

                container.addView(card);
            }
        }
    }

    private void updateStarIcon(ImageView star, boolean isFav) {
        if (isFav) {
            star.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            star.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }
}
