package com.example.cs465prototype;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;


//public class BusinessProfileActivity extends AppCompatActivity {
//
//    private Business currentBusiness;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_business_profile);
//
//        // Back + star
//        Button backBtn = findViewById(R.id.back);
//        ImageButton starBtn = findViewById(R.id.star);
//        ImageView mainImg = findViewById(R.id.bussinesImg);
//
//        // Text fields
//        TextView name     = findViewById(R.id.bussinessName);
//        TextView category = findViewById(R.id.category);
//        TextView location = findViewById(R.id.location);
//        TextView ci       = findViewById(R.id.ci);
//        TextView ownedBy  = findViewById(R.id.owned_by);
//        TextView price    = findViewById(R.id.price_range);
//        TextView desc     = findViewById(R.id.description);
//
//        // Get business ID that was passed
//        String businessId = getIntent().getStringExtra("business_id");
//
////        currentBusiness = BusinessDataManager.getInstance().businessMap.get(businessId);
////
////        if (currentBusiness == null) {
////            Toast.makeText(this, "Error loading business", Toast.LENGTH_SHORT).show();
////            finish();
////            return;
////        }
//
//        // Get business object
//        BusinessDataManager dm = BusinessDataManager.getInstance();
//        Business b = dm.businessMap.get(businessId);
//
//        if (b != null) {
//            // --- Fill text fields ---
//            name.setText(b.name);
//            category.setText("Category: " + b.category);
//            location.setText("Location: " + b.location);
//            ci.setText("Contact: " + b.contact);
//            ownedBy.setText("Owned By: " + b.owner);
//            price.setText("Price Range: " + b.price_range);
//            desc.setText("Description: " + b.description);
//
//            // --- Load image same way Discover did ---
//            String imgName = b.photo
//                    .toLowerCase()
//                    .replace(".jpg", "")
//                    .replace(".jpeg", "")
//                    .replace(".png", "")
//                    .replace("-", "_")
//                    .replace(" ", "_");
//
//            int imgRes = getResources().getIdentifier(imgName, "drawable", getPackageName());
//
//            if (imgRes != 0)
//                mainImg.setImageResource(imgRes);
//        }
//
//        // Set initial star icon
//        updateStarIcon(starBtn, currentBusiness.favorited);
//
//        // Toggle favorite on click
//        starBtn.setOnClickListener(v -> {
//            currentBusiness.favorited = !currentBusiness.favorited;
//            updateStarIcon(starBtn, currentBusiness.favorited);
//        });
//
//
//        // Back button logic
//        backBtn.setOnClickListener(v -> finish());
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        // Bottom navigation
//        BottomNavigationView nav = findViewById(R.id.bottom_navigation);
//        nav.setOnItemSelectedListener(item -> {
//            int id = item.getItemId();
//
//            if (id == R.id.nav_home) {
//                startActivity(new Intent(BusinessProfileActivity.this, MainActivity.class));
//                return true;
//            } else if (id == R.id.nav_discover) {
//                startActivity(new Intent(BusinessProfileActivity.this, discoverActivity.class));
//                return true;
//            } else if (id == R.id.nav_search) {
//                startActivity(new Intent(BusinessProfileActivity.this, searchActivity.class));
//                return true;
//            } else if (id == R.id.nav_favorites) {
//                startActivity(new Intent(BusinessProfileActivity.this, favoritesActivity.class));
//                return true;
//            }
//
//            return false;
//        });
//    }
//
//    private void updateStarIcon(ImageButton starBtn, boolean isFav) {
//        if (isFav) {
//            starBtn.setImageResource(android.R.drawable.btn_star_big_on);
//        } else {
//            starBtn.setImageResource(android.R.drawable.btn_star_big_off);
//        }
//    }
//
//
//}

public class BusinessProfileActivity extends AppCompatActivity {

    private Business currentBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_business_profile);

        // Get views
        Button backBtn = findViewById(R.id.back);
        ImageButton starBtn = findViewById(R.id.star);
        ImageView mainImg = findViewById(R.id.bussinesImg);

        TextView name = findViewById(R.id.bussinessName);
        TextView category = findViewById(R.id.category);
        TextView location = findViewById(R.id.location);
        TextView ci = findViewById(R.id.ci);
        TextView owned = findViewById(R.id.owned_by);
        TextView price = findViewById(R.id.price_range);
        TextView desc = findViewById(R.id.description);

        // Retrieve which business was clicked
        String businessId = getIntent().getStringExtra("business_id");
        currentBusiness = BusinessDataManager.getInstance().businessMap.get(businessId);

        if (currentBusiness == null) {
            Toast.makeText(this, "Error loading business", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Populate UI
        name.setText(currentBusiness.name);
        category.setText("Category: " + currentBusiness.category);
        location.setText("Location: " + currentBusiness.location);
        ci.setText("Contact: " + currentBusiness.contact);
        owned.setText("Owned By: " + currentBusiness.owner);
        price.setText("Price Range: " + currentBusiness.price_range);
        desc.setText("Description: " + currentBusiness.description);

//        // Load image
//        int imgRes = getResources().getIdentifier(
//                currentBusiness.photo.replace(".jpg", ""),
//                "drawable",
//                getPackageName()
//        );
//        if (imgRes != 0) {
//            mainImg.setImageResource(imgRes);
//        }
        // Load image (robust version)
        String imgName = currentBusiness.photo
                .toLowerCase()
                .replace(".jpg", "")
                .replace(".jpeg", "")
                .replace(".png", "")
                .replace("-", "_")
                .replace(" ", "_");

        int imgRes = getResources().getIdentifier(imgName, "drawable", getPackageName());

        if (imgRes != 0) {
            mainImg.setImageResource(imgRes);
        }


        // Set initial star icon
        updateStarIcon(starBtn, currentBusiness.favorited);

        // Toggle favorite on click and also includes popup error correction alert if you want to unadded it from favorites or when you add it
        starBtn.setOnClickListener(v -> {
            if (currentBusiness.favorited) {
                new AlertDialog.Builder(BusinessProfileActivity.this)
                        .setMessage("Are you sure you want to remove this business from your Favorites?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            currentBusiness.favorited = false;
                            updateStarIcon(starBtn, false);
                            Toast.makeText(BusinessProfileActivity.this, "Removed from Favorites", Toast.LENGTH_SHORT).show();

                        })
                        .setNegativeButton("Cancel", (dialog, which) -> {
                           dialog.dismiss();

                        }).show();
            } else {
                currentBusiness.favorited = true;
                updateStarIcon(starBtn, true);
                Toast.makeText(BusinessProfileActivity.this, "Added to Favorites", Toast.LENGTH_SHORT).show();
            }

        });

        // Back button
        backBtn.setOnClickListener(v -> finish());

        // Insets (unchanged)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Bottom navigation
        BottomNavigationView nav = findViewById(R.id.bottom_navigation);
        nav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                startActivity(new Intent(BusinessProfileActivity.this, MainActivity.class));
                return true;
            } else if (id == R.id.nav_discover) {
                startActivity(new Intent(BusinessProfileActivity.this, discoverActivity.class));
                return true;
            } else if (id == R.id.nav_search) {
                startActivity(new Intent(BusinessProfileActivity.this, searchActivity.class));
                return true;
            } else if (id == R.id.nav_favorites) {
                startActivity(new Intent(BusinessProfileActivity.this, favoritesActivity.class));
                return true;
            }

            return false;
        });
    }

    private void updateStarIcon(ImageButton starBtn, boolean isFav) {
        if (isFav) {
            starBtn.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            starBtn.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }
}
