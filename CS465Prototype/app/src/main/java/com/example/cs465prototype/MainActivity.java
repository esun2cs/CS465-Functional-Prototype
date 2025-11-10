package com.example.cs465prototype;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void launchSearch(View v) {
        // launch Search page

        // Instantiate a new object of type intent, assigned to variable i
        Intent i = new Intent(this, SearchActivity.class);
        startActivity(i);

    }

    public void launchDiscover(View v) {
        // launch Search page

        // Instantiate a new object of type intent, assigned to variable i
        Intent i = new Intent(this, discoverActivity.class);
        startActivity(i);

    }

    public void launchFavorites(View v) {
        // launch Search page

        // Instantiate a new object of type intent, assigned to variable i
        Intent i = new Intent(this, favoritesActivity.class);
        startActivity(i);

    }
}