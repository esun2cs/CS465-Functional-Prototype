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

    private final Context context;
    // already filtered list passed in from searchActivity
    private final ArrayList<Business> businesses;

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

        // Text
        holder.name.setText(b.name);
        holder.added.setText("Matches search");
        holder.category.setText(b.category);
        holder.description.setText(b.description);

        // Image
        String imageName = b.photo.toLowerCase()
                .replace(".jpg", "")
                .replace(".jpeg", "")
                .replace(".png", "")
                .replace("-", "_")
                .replace(" ", "_");

        int imgRes = context.getResources().getIdentifier(
                imageName, "drawable", context.getPackageName());
        if (imgRes != 0) {
            holder.photo.setImageResource(imgRes);
        } else {
            holder.photo.setImageResource(R.drawable.ic_launcher_background);
        }

        // Initial star state
        updateStarIcon(holder.star, b.favorited);

        holder.star.setOnClickListener(v -> {
            b.favorited = !b.favorited;
            updateStarIcon(holder.star, b.favorited);
        });

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

//    // Optional: called from filterActivity result – right now just refreshes
//    public void applyFilters(String location, int maxPrice, String tag) {
//        // If you later want advanced filters, you can filter `businesses` here.
//        notifyDataSetChanged();
//    }

    private void updateStarIcon(ImageView starView, boolean isFav) {
        if (isFav) {
            starView.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            starView.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, added, category, description;
        ImageView photo;
        ImageView star;   // NOTE: uses the star in business_card.xml, keeps it consistent with discover and favorites pages

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.business_name);
            added = itemView.findViewById(R.id.business_added);
            category = itemView.findViewById(R.id.business_category);
            description = itemView.findViewById(R.id.business_description);
            photo = itemView.findViewById(R.id.business_photo);
            star = itemView.findViewById(R.id.business_favorite_star);
        }
    }
}
