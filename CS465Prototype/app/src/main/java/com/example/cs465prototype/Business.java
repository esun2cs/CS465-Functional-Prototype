package com.example.cs465prototype;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Business {
    public String id;
    public String name;
    public String category;
    public String location;
    public String contact;
    public String owner;
    public String price_range;
    public String description;
    public String photo;
    public boolean favorited;
    public boolean isNew;
    public ArrayList<String> tags;
    public boolean isOnCampus;

    public Business(JSONObject obj) {
        id = obj.optString("id");
        name = obj.optString("name");
        category = obj.optString("category");
        location = obj.optString("location");
        contact = obj.optString("contact");
        owner = obj.optString("owner");
        price_range = obj.optString("price_range");
        description = obj.optString("description");
        photo = obj.optString("photo");
        favorited = obj.optString("favorited").equals("true");
        isNew = obj.optString("new").equals("true");

        isOnCampus = obj.optBoolean("is_on_campus", false);

        tags = new ArrayList<>();
        JSONArray tagArray = obj.optJSONArray("tags");
        if (tagArray != null) {
            for (int i = 0; i < tagArray.length(); i++) {
                tags.add(tagArray.optString(i));
            }
        }
    }
}
