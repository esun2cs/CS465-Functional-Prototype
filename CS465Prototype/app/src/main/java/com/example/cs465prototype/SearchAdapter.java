package com.example.cs465prototype;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Business> businesses;

    public SearchAdapter(Context context, ArrayList<Business> businesses) {
        this.context = context;
        this.businesses = businesses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.business_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Business b = businesses.get(position);

        holder.name.setText(b.name);
        holder.added.setText("Matches search");
        holder.category.setText(b.category);
        holder.description.setText(b.description);

        // Load image
        int imgRes = context.getResources().getIdentifier(
                b.photo.replace(".jpg","").replace(".png",""),
                "drawable",
                context.getPackageName()
        );
        if (imgRes != 0) holder.photo.setImageResource(imgRes);

        // Click → open profile
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, BusinessProfileActivity.class);
            i.putExtra("business_id", b.id);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return businesses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, added, category, description;
        ImageView photo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.business_name);
            added = itemView.findViewById(R.id.business_added);
            category = itemView.findViewById(R.id.business_category);
            description = itemView.findViewById(R.id.business_description);
            photo = itemView.findViewById(R.id.business_photo);
        }
    }
}
