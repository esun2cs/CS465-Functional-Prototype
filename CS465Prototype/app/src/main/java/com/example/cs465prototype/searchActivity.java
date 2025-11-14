package com.example.cs465prototype;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import kotlin.collections.ArrayDeque;

public class searchActivity extends AppCompatActivity {

//    private SearchView searchView;
//    private List<Item> itemList;
//    private ItemAdapter itemAdapter;
//    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        searchView = findViewById(R.id.searchView);
//        searchView.clearFocus();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                filterList(newText);
//                return true;
//            }
//
//        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            //        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    // do something
                    Intent i = new Intent(searchActivity.this, MainActivity.class);
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
        // launch Search page

        // Instantiate a new object of type intent, assigned to variable i
        Intent i = new Intent(this, searchActivity.class);
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

//    private void filterList(String newText) {
//        List<Item> filteredList = new ArrayList<>();
//        for (Item item : itemList){
//            if (item.getItemName())
//        }
//    }
    public void launchBusinessProfile(View v) {
        // launch business page
        Intent i = new Intent(this, BusinessProfileActivity.class);
        startActivity(i);
    }
}