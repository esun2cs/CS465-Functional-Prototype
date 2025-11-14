package com.example.cs465prototype;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class favoritesActivity extends AppCompatActivity {

    private static final String PREFS = "favorites_pref";

    private CardView cardClay, cardBouquet;
    private ImageView starClay, starBouquet;

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

        // Cards
        cardClay = findViewById(R.id.card_clay);
        cardBouquet = findViewById(R.id.card_bouquet);

        // Stars
        starClay = findViewById(R.id.star_clay);
        starBouquet = findViewById(R.id.star_bouquet);

        // Load UI state
        refreshUI();

        // Star click listeners
        starClay.setOnClickListener(v -> toggleFavorite("clay"));
        starBouquet.setOnClickListener(v -> toggleFavorite("bouquet"));

        // Bottom nav
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this::handleNavigation);
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

    // --- FAVORITE MANAGEMENT ---

    private void toggleFavorite(String key) {
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        boolean current = prefs.getBoolean(key, true);

        prefs.edit().putBoolean(key, !current).apply();
        refreshUI();
    }

    private void refreshUI() {
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);

        boolean clayFav = prefs.getBoolean("clay", true);
        boolean bouquetFav = prefs.getBoolean("bouquet", true);

        // Show only starred cards
        cardClay.setVisibility(clayFav ? View.VISIBLE : View.GONE);
        cardBouquet.setVisibility(bouquetFav ? View.VISIBLE : View.GONE);

        // Update star icons
        updateStar(starClay, clayFav);
        updateStar(starBouquet, bouquetFav);
    }

    private void updateStar(ImageView star, boolean isFav) {
        if (isFav) {
            star.setImageResource(R.drawable.ic_star);  // filled
        } else {
            star.setImageResource(R.drawable.ic_star_border);  // empty
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshUI();
    }
}
