package com.example.cs465prototype;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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

        // Handle window insets for edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Spinner setup
        Spinner spinner = findViewById(R.id.spinner_service);
        String[] services = {"nails", "florist", "jewelry", "tutoring", "sewing/hemming", "crafts"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, services);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // X button navigates to SearchActivity
        ImageButton closeButton = findViewById(R.id.btn_close_popup);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(filterActivity.this, searchActivity.class);
                startActivity(intent);
                finish(); // close filterActivity
            }
        });

        // Clear button resets all filters
        Button clearButton = findViewById(R.id.btn_clear_filters);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset Spinner
                spinner.setSelection(0);

                // Reset CheckBoxes
                CheckBox onCampus = findViewById(R.id.checkbox_on_campus);
                CheckBox offCampus = findViewById(R.id.checkbox_off_campus);
                onCampus.setChecked(false);
                offCampus.setChecked(false);

                // Reset SeekBar
                SeekBar seekBar = findViewById(R.id.seekbar_price);
                seekBar.setProgress(0);
            }
        });

        // Bottom navigation setup
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    Intent i = new Intent(filterActivity.this, MainActivity.class);
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

    public void launchFilter(View v) {
        Intent i = new Intent(this, filterActivity.class);
        startActivity(i);
    }
}
