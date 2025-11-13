package com.example.cs465prototype;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class BusinessProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_business_profile);
        //views
        Button backBtn = findViewById(R.id.back);
        ImageButton starBtn = findViewById(R.id.star);
        ImageView mainImg = findViewById(R.id.bussinesImg);

        //back button
        backBtn.setOnClickListener(v -> {
            if (getParentActivityIntent() != null || getCallingActivity() != null) {
                finish();
            } else {
                Intent i = new Intent(BusinessProfileActivity.this, searchActivity.class);
                startActivity(i);
                finish();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}