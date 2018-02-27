package com.udacity.sandwichclub.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich;
        String mainName;
        JSONArray akaArray;
        List<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin;
        String description;
        String image;
        JSONArray ingArray;
        List<String> ingredients = new ArrayList<>();

        try {
            JSONObject sandwichData = new JSONObject(json);
            JSONObject name = sandwichData.getJSONObject("name");

            mainName = name.getString("mainName");
            akaArray = name.getJSONArray("alsoKnownAs");

            for(int i = 0; i < akaArray.length(); i++) {
                alsoKnownAs.add(akaArray.getString(i));
            }

            placeOfOrigin = sandwichData.getString("placeOfOrigin");
            description = sandwichData.getString("description");
            image = sandwichData.getString("image");
            ingArray = sandwichData.getJSONArray("ingredients");

            for (int i = 0; i < ingArray.length(); i++) {
                ingredients.add(ingArray.getString(i));
            }

            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException ex) {
            Log.v("JSONUtils", "Parsing failed");
            return null;
        }

        return sandwich;
    }
}
