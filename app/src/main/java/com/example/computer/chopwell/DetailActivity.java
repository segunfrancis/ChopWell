package com.example.computer.chopwell;

import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailActivity extends AppCompatActivity {

    private TextView description, preparation, recipe;
    private ImageView imageView;
    private FloatingActionButton fab;
    private String itemId, mealNameString, descriptionString, preparationString, recipeString, imageURLString, userId;
    private DatabaseReference myRef;
    private FirebaseDatabase database;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        final CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(DetailActivity.this);
        circularProgressDrawable.setStrokeWidth(20.0f);
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

        // Retrieving data from intent
        itemId = intent.getStringExtra(MealViewHolder.ID);
        mealNameString = intent.getStringExtra(MealViewHolder.MEAL_NAME);
        descriptionString = intent.getStringExtra(MealViewHolder.DESCRIPTION);
        preparationString = intent.getStringExtra(MealViewHolder.PREPARATION);
        recipeString = intent.getStringExtra(MealViewHolder.RECIPE);
        imageURLString = intent.getStringExtra(MealViewHolder.IMAGE_URL);
        userId = intent.getStringExtra(MealViewHolder.USERID);

        Glide.with(DetailActivity.this)
                .load(imageURLString)
                .placeholder(circularProgressDrawable)
                .error(R.drawable.error_image)
                .into(imageView);

        description.setText(descriptionString);
        preparation.setText(preparationString);
        recipe.setText(recipeString);

        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        String preferenceId = preferences.getString("itemId", "N/A");

        if (preferences != null) {
            if (itemId.equals(preferenceId)) {
                fabImage.setLevel(1);
            }
        }


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


                    myRef.child("favorites").child(userId).setValue();

                    editor.putString("itemId", itemId);
                    editor.apply();

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
