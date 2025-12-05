package com.example.cs465prototype;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class filterActivity extends AppCompatActivity {

    private Spinner spinner;
    private CheckBox onCampus, offCampus;
    private SeekBar seekbarPrice;
    private TextView labelPrice;
    private Button btnApply, btnClear;
    private ImageButton btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_filter);

        // Edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, 0); // systemBars.bottom nav bar gap fix
            return insets;
        });

        // Find views
        spinner = findViewById(R.id.spinner_service);
        onCampus = findViewById(R.id.checkbox_on_campus);
        offCampus = findViewById(R.id.checkbox_off_campus);
        seekbarPrice = findViewById(R.id.seekbar_price);
        labelPrice = findViewById(R.id.label_price_value);
        btnApply = findViewById(R.id.btn_apply_filters);
        btnClear = findViewById(R.id.btn_clear_filters);
        btnClose = findViewById(R.id.btn_close_popup);

        // Tag spinner set up
        ArrayAdapter<CharSequence> tagAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.tags_array,
                android.R.layout.simple_spinner_item
        );
        tagAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(tagAdapter);


        // price icon slider
        seekbarPrice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int value, boolean fromUser) {
                labelPrice.setText("$" + value);

                // Move label to follow thumb position
                float x = seekBar.getX()
                        + seekBar.getThumb().getBounds().centerX()
                        - (labelPrice.getWidth() / 2f);

                labelPrice.setX(x);
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Set initial label position (price starts at 0)
        labelPrice.setText("$" + seekbarPrice.getProgress());

       //close button
        btnClose.setOnClickListener(v -> finish());

        // ============================
        // CLEAR FILTERS
        // ============================
        btnClear.setOnClickListener(v -> {
            spinner.setSelection(0); // "Any"
            onCampus.setChecked(false);
            offCampus.setChecked(false);
            seekbarPrice.setProgress(100);
            labelPrice.setText("$100");
        });

        //apply filters
        btnApply.setOnClickListener(v -> {

            // Tag filter
            String tag = spinner.getSelectedItem().toString().toLowerCase();
            if (tag.equals("any")) tag = "any";

            // Location filter
            String location = "any";
            if (onCampus.isChecked()) location = "on";
            if (offCampus.isChecked()) location = "off";

            // Price filter
            int maxPrice = seekbarPrice.getProgress();

            // Send filters back
            Intent result = new Intent();
            result.putExtra("tagFilter", tag);
            result.putExtra("locationFilter", location);
            result.putExtra("maxPrice", maxPrice);

            setResult(RESULT_OK, result);
            finish();
        });

        //bottom navigation
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
