package com.example.cs465prototype;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class filterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_filter);

        // Edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        // ============================
        // SPINNER SETUP (FIXED)
        // ============================
        Spinner spinner = findViewById(R.id.spinner_service);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.service_options,                  // ← Now using strings.xml
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Close button → go back to searchActivity
        ImageButton closeButton = findViewById(R.id.btn_close_popup);
        closeButton.setOnClickListener(v -> {
            startActivity(new Intent(filterActivity.this, searchActivity.class));
            finish();
        });

        // Clear button resets all filters
        Button clearButton = findViewById(R.id.btn_clear_filters);
        clearButton.setOnClickListener(v -> {
            spinner.setSelection(0);

            CheckBox onCampus = findViewById(R.id.checkbox_on_campus);
            CheckBox offCampus = findViewById(R.id.checkbox_off_campus);
            SeekBar seek = findViewById(R.id.seekbar_price);

            onCampus.setChecked(false);
            offCampus.setChecked(false);
            seek.setProgress(0);
        });

        // Apply Filters → go to Search page
        Button applyButton = findViewById(R.id.btn_apply_filters);
        applyButton.setOnClickListener(v -> {
            Intent intent = new Intent(filterActivity.this, searchActivity.class);
            startActivity(intent);
            finish();
        });

        // Bottom navigation
        BottomNavigationView nav = findViewById(R.id.bottom_navigation);
        nav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                startActivity(new Intent(filterActivity.this, MainActivity.class));
                return true;
            } else if (id == R.id.nav_discover) {
                startActivity(new Intent(filterActivity.this, discoverActivity.class));
                return true;
            } else if (id == R.id.nav_search) {
                startActivity(new Intent(filterActivity.this, searchActivity.class));
                return true;
            } else if (id == R.id.nav_favorites) {
                startActivity(new Intent(filterActivity.this, favoritesActivity.class));
                return true;
            }

            return false;
        });
    }
}
