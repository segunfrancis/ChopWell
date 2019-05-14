package com.example.computer.chopwell;

import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.computer.chopwell.adapter.MealAdapter.MealViewHolder;
import com.example.computer.chopwell.model.MealModel;
import com.google.gson.Gson;

public class DetailActivity extends AppCompatActivity {

    private TextView description, preparation, recipe;
    private ImageView imageView;
    private FloatingActionButton fab;
    private String itemId;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(DetailActivity.this);
        circularProgressDrawable.setStrokeWidth(25.0f);
        circularProgressDrawable.setColorSchemeColors(Color.WHITE, Color.GREEN, Color.rgb(216, 27, 96));
        circularProgressDrawable.setCenterRadius(50.0f);
        circularProgressDrawable.start();

        final Intent intent = getIntent();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(intent.getStringExtra(MealViewHolder.MEAL_NAME));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        fab = findViewById(R.id.fab);
        imageView = findViewById(R.id.meal_image_large);
        description = findViewById(R.id.description_large);
        preparation = findViewById(R.id.preparation_large);
        recipe = findViewById(R.id.recipe_large);

        final Drawable fabImage = fab.getDrawable();

        Glide.with(DetailActivity.this)
                .load(intent.getStringExtra(MealViewHolder.IMAGE_URL))
                .placeholder(circularProgressDrawable)
                .error(R.drawable.error_image)
                .into(imageView);

        description.setText(intent.getStringExtra(MealViewHolder.DESCRIPTION));
        preparation.setText(intent.getStringExtra(MealViewHolder.PREPARATION));
        recipe.setText(intent.getStringExtra(MealViewHolder.RECIPE));

        itemId = intent.getStringExtra("id");

        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        String preferenceId = preferences.getString("itemId", "N/A");

        if (preferences != null) {
            if (itemId.equals(preferenceId)) {
                fabImage.setLevel(1);
            }
        }

/*        SharedPreferences preferences = getSharedPreferences("favorite", Context.MODE_PRIVATE);
        String jsonString = preferences.getString("fav_key", "N/A");

        // Deserialization
        Gson gson = new Gson();
        MealModel mealModel = gson.fromJson(jsonString, MealModel.class);
        if (mealModel != null) {
            description.setText(mealModel.getDescription());
            preparation.setText(mealModel.getPreparation());
            recipe.setText(mealModel.getRecipe());
            Glide.with(DetailActivity.this)
                    .load(mealModel.getImageURL())
                    .placeholder(circularProgressDrawable)
                    .error(R.drawable.error_image)
                    .into(imageView);
            fabImage.setLevel(1);
        }*/

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                String snackBarMessage;
                // Drawable fabImage = fab.getDrawable();
                if (fabImage.getLevel() == 0) {
                    fabImage.setLevel(1);
                    snackBarMessage = "Added to Favorite";
                    Snackbar.make(view, snackBarMessage, Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();

                    editor.putString("itemId", itemId);
                    editor.apply();
/*
                    // Adding to Shared Preference
                    MealModel mealModel = new MealModel();
                    mealModel.setMealName(intent.getStringExtra(MealViewHolder.MEAL_NAME));
                    mealModel.setDescription(intent.getStringExtra(MealViewHolder.DESCRIPTION));
                    mealModel.setPreparation(intent.getStringExtra(MealViewHolder.PREPARATION));
                    mealModel.setRecipe(intent.getStringExtra(MealViewHolder.RECIPE));
                    mealModel.setImageURL(intent.getStringExtra(MealViewHolder.IMAGE_URL));

                    SharedPreferences preferences = getSharedPreferences("favorite", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    // Serialisation
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(mealModel, MealModel.class);

                    editor.putString("fav_key", jsonString);
                    editor.apply();*/

                } else if (fabImage.getLevel() == 1) {
                    fabImage.setLevel(0);
                    snackBarMessage = "Removed from Favorite";
                    Snackbar.make(view, snackBarMessage, Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();

                    editor.remove("itemId");
                }
            }
        });
    }
}
