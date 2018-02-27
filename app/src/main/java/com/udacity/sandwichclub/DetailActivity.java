package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView mDescription;
    TextView mOrigin;
    TextView mIngredients;
    TextView mAka;
    TextView mMainName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        //mMainName = findViewById(R.id.);
        mAka = findViewById(R.id.also_known_tv);
        mOrigin = findViewById(R.id.origin_tv);
        mDescription = findViewById(R.id.description_tv);
        mIngredients = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            Log.v("DetailActivity", "No intent");
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);

        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            Log.v("DetailActivity", "No EXTRA_POSITION");
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            Log.v("DetailActivity", "No sandwich");
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        String aka = "";
        String ingredients = "";
        for ( int i = 0; i < sandwich.getAlsoKnownAs().toArray().length; i++) {
            aka += sandwich.getAlsoKnownAs().get(i);
            if(i != sandwich.getAlsoKnownAs().toArray().length - 1 ) {
                aka += "; ";
            }
        }
        for( int i = 0; i < sandwich.getIngredients().toArray().length; i++) {
            ingredients += sandwich.getIngredients().get(i);
            if(i != sandwich.getIngredients().toArray().length - 1) {
                ingredients += "; ";
            }
        }
        mIngredients.setText(ingredients);
        mAka.setText(aka);
        mDescription.setText(sandwich.getDescription());
        mOrigin.setText(sandwich.getPlaceOfOrigin());
    }
}
