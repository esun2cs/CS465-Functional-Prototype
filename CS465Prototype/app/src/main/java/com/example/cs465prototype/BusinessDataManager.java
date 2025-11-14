package com.example.cs465prototype;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class BusinessDataManager {

    private static BusinessDataManager instance;

    public ArrayList<Business> allBusinesses = new ArrayList<>();
    public HashMap<String, Business> businessMap = new HashMap<>();

    private boolean isLoaded = false;

    private BusinessDataManager() {}

    public static BusinessDataManager getInstance() {
        if (instance == null) {
            instance = new BusinessDataManager();
        }
        return instance;
    }

    // Load once on app launch
    public void loadFromJson(Context context) {
        if (isLoaded) {
            System.out.println(">>> JSON ALREADY LOADED, SKIPPING");
            return; // prevents multiple reloads
        }

        try {
            System.out.println("ATTEMPTING TO LOAD BUSINESS JSON");
            InputStream is = context.getAssets().open("businesses.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String jsonStr = new String(buffer, "UTF-8");
            System.out.println("JSON STRING LENGTH" + jsonStr.length());

            JSONArray array = new JSONArray(jsonStr);
            System.out.println(">>> JSON array size = " + array.length());

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Business b = new Business(obj);
                allBusinesses.add(b);
                businessMap.put(b.id, b);
            }

            isLoaded = true;
            System.out.println(">>> Finished loading JSON.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}