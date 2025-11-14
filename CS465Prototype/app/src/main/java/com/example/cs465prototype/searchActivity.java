//package com.example.cs465prototype;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
////import android.widget.SearchView;
//import androidx.appcompat.widget.SearchView;
//
//import androidx.activity.EdgeToEdge;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//
//import java.util.ArrayList;
//
//import kotlin.collections.ArrayDeque;
//
//public class searchActivity extends AppCompatActivity {
//
//    //    private SearchView searchView;
////    private List<Item> itemList;
////    private ItemAdapter itemAdapter;
////    private RecyclerView recyclerView;
////
//    private SearchView searchView;
//    private ListView listView;
//    private ArrayAdapter<String> arrayAdapter;
//
//    //    ListView listView;
////    ArrayAdapter<String> arrayAdapter;
////    String[] businessNameList = {"Nailed It by Lily", "FadeLab by Marcus", "Charm Studio by Priya", "Dorm Dishes by Andy", "BloomAndCo"};
//    String[] businessNameList = {"Nailed It by Lily", "FadeLab by Marcus", "Charm Studio by Priya", "Dorm Dishes by Andy", "Bloom&Co"};
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_search);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        searchView = findViewById(R.id.searchView);
//        listView = findViewById(R.id.search_results_list);
//
//        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, businessNameList);
//        listView.setAdapter(arrayAdapter);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                arrayAdapter.getFilter().filter(s);
//                return true;
//            }
//        });
////
//        // Click handler for results (optional now)
//        listView.setOnItemClickListener((parent, view, position, id) -> {
//            String selected = arrayAdapter.getItem(position);
//            // TODO: Pass selected business to profile page of CORRECT BUSINESS, this businessactivity is still hardcoded
//            launchBusinessProfile(null);
//        });
//
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
//            //        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                int id = item.getItemId();
//                if (id == R.id.nav_home) {
//                    // do something
//                    Intent i = new Intent(searchActivity.this, MainActivity.class);
//                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                    startActivity(i);
//                    return true;
//                } else if (id == R.id.nav_discover) {
//                    launchDiscover(null);
//                    return true;
//                } else if (id == R.id.nav_search) {
//                    launchSearch(null);
//                    return true;
//                } else if (id == R.id.nav_favorites) {
//                    launchFavorites(null);
//                    return true;
//                }
//                return false;
//            }
//        });
//    }
//
//    public void launchFilter(View v) {
//        Intent i = new Intent(this, filterActivity.class);
//        startActivity(i);
//    }
//
//    public void launchSearch(View v) {
//        // launch Search page
//
//        // Instantiate a new object of type intent, assigned to variable i
//        Intent i = new Intent(this, searchActivity.class);
//        startActivity(i);
//
//    }
//
//    public void launchDiscover(View v) {
//        // launch Search page
//
//        // Instantiate a new object of type intent, assigned to variable i
//        Intent i = new Intent(this, discoverActivity.class);
//        startActivity(i);
//
//    }
//
//    public void launchFavorites(View v) {
//        // launch Search page
//
//        // Instantiate a new object of type intent, assigned to variable i
//        Intent i = new Intent(this, favoritesActivity.class);
//        startActivity(i);
//    }
//    public void launchBusinessProfile(View v) {
//        // launch business page
//        Intent i = new Intent(this, BusinessProfileActivity.class);
//        startActivity(i);
//    }
//}

package com.example.cs465prototype;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class searchActivity extends AppCompatActivity {

    private SearchView searchView;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;

    // Full list from JSON
    private ArrayList<Business> allBusinesses = new ArrayList<>();
    // Filtered list backing the ListView
    private ArrayList<Business> filteredBusinesses = new ArrayList<>();
    // Just the names for the adapter
    private ArrayList<String> nameList = new ArrayList<>();

    String[] businessNameList = {"Nailed It by Lily", "FadeLab by Marcus", "Charm Studio by Priya", "Dorm Dishes by Andy", "Bloom&Co"};

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

        searchView = findViewById(R.id.searchView);
        listView = findViewById(R.id.search_results_list);

        // 1. Load data from BusinessDataManager
        BusinessDataManager dm = BusinessDataManager.getInstance();
        if (dm.allBusinesses.size() == 0) {
            dm.loadFromJson(this);
        }
        allBusinesses = dm.allBusinesses;

        // 2. Start with everything visible
        filteredBusinesses.clear();
        filteredBusinesses.addAll(allBusinesses);
        rebuildNameList();

        // 3. Set up adapter with names from filteredBusinesses
        arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                nameList
        );
        listView.setAdapter(arrayAdapter);

        // 4. Search filtering
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                filterBusinesses(query);
                return true;
            }
        });

        // 5. Click → open correct BusinessProfileActivity
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Business selected = filteredBusinesses.get(position);
            Intent i = new Intent(searchActivity.this, BusinessProfileActivity.class);
            i.putExtra("business_id", selected.id);
            startActivity(i);
        });

        // Bottom nav
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    Intent i = new Intent(searchActivity.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(i);
                    return true;
                } else if (id == R.id.nav_discover) {
                    launchDiscover(null);
                    return true;
                } else if (id == R.id.nav_search) {
                    // already here
                    return true;
                } else if (id == R.id.nav_favorites) {
                    launchFavorites(null);
                    return true;
                }
                return false;
            }
        });
    }

    // Rebuild the name list whenever filteredBusinesses changes
    private void rebuildNameList() {
        nameList.clear();
        for (Business b : filteredBusinesses) {
            nameList.add(b.name);
        }
    }

    // Filter by name (and optionally category)
    private void filterBusinesses(String query) {
        String q = query.toLowerCase().trim();

        filteredBusinesses.clear();

        if (q.isEmpty()) {
            // Show all if empty search
            filteredBusinesses.addAll(allBusinesses);
        } else {
            for (Business b : allBusinesses) {
                if (b.name.toLowerCase().contains(q)
                        || b.category.toLowerCase().contains(q)) {
                    filteredBusinesses.add(b);
                }
            }
        }

        rebuildNameList();
        arrayAdapter.notifyDataSetChanged();
    }

    // Existing navigation helpers

    public void launchFilter(View v) {
        Intent i = new Intent(this, filterActivity.class);
        startActivity(i);
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

    public void launchBusinessProfile(View v) {
        // Not used by the ListView anymore, but you can keep or remove
        Intent i = new Intent(this, BusinessProfileActivity.class);
        startActivity(i);
    }
}
