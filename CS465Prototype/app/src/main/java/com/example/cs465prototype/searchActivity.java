package com.example.cs465prototype;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class searchActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerView;

    private TextView emptyView;

    private ArrayList<Business> allBusinesses = new ArrayList<>();
    private ArrayList<Business> filteredBusinesses = new ArrayList<>();

    private SearchAdapter adapter;

    // New: launcher for filters
    private androidx.activity.result.ActivityResultLauncher<Intent> filterLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0); // systemBars.bottom caused gap at bottom before
            return insets;
        });

        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.search_results_recycler);
        emptyView = findViewById(R.id.empty_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        BusinessDataManager dm = BusinessDataManager.getInstance();
        if (dm.allBusinesses.isEmpty()) {
            dm.loadFromJson(this);
        }

        allBusinesses = dm.allBusinesses;
        filteredBusinesses.addAll(allBusinesses);

        adapter = new SearchAdapter(this, filteredBusinesses);
        recyclerView.setAdapter(adapter);

        updateEmptyState();

        // Register filter handler
        filterLauncher = registerForActivityResult(
                new androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                        String location = result.getData().getStringExtra("locationFilter");
                        int maxPrice = result.getData().getIntExtra("maxPrice", 100);
                        String tag = result.getData().getStringExtra("tagFilter");

                        adapter.applyFilters(location, maxPrice, tag);
                    }
                }
        );

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String s) { return false; }
            @Override public boolean onQueryTextChange(String text) {
                filter(text);
                return true;
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_search);
        bottomNavigationView.setOnItemSelectedListener(this::handleNavigation);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (adapter != null && searchView != null) {
            // Re-apply current search text so filteredBusinesses is rebuilt
            String q = searchView.getQuery() != null
                    ? searchView.getQuery().toString()
                    : "";
            filter(q);   // this already calls adapter.notifyDataSetChanged()
        }
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
            return true;
        } else if (id == R.id.nav_favorites) {
            startActivity(new Intent(this, favoritesActivity.class));
            return true;
        }
        return false;
    }

    private void filter(String q) {
        q = q.toLowerCase();

        filteredBusinesses.clear();

        for (Business b : allBusinesses) {
            if (b.name.toLowerCase().contains(q) ||
                    b.category.toLowerCase().contains(q)) {
                filteredBusinesses.add(b);
            }
        }

        adapter.notifyDataSetChanged();
        updateEmptyState();
    }

    private void updateEmptyState() {
        if (filteredBusinesses.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }


    // uses launcher instead of startActivity
    public void launchFilter(View v) {
        Intent i = new Intent(this, filterActivity.class);
        filterLauncher.launch(i);
    }
}
