package com.example.computer.chopwell;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
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

public class DetailActivity extends AppCompatActivity {

    private TextView description, preparation, recipe;
    private ImageView imageView;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(DetailActivity.this);
        circularProgressDrawable.setStrokeWidth(35.0f);
        circularProgressDrawable.setColorSchemeColors(Color.WHITE, Color.GREEN, Color.rgb(216, 27, 96));
        circularProgressDrawable.setCenterRadius(50.0f);
        circularProgressDrawable.start();

        Intent intent = getIntent();

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

        imageView = findViewById(R.id.meal_image_large);
        description = findViewById(R.id.description_large);
        preparation = findViewById(R.id.preparation_large);
        recipe = findViewById(R.id.recipe_large);

        Glide.with(DetailActivity.this)
                .load(intent.getStringExtra(MealViewHolder.IMAGE_URL))
                .placeholder(circularProgressDrawable).error(R.drawable.error_image).into(imageView);

        description.setText(intent.getStringExtra(MealViewHolder.DESCRIPTION));
        preparation.setText(intent.getStringExtra(MealViewHolder.PREPARATION));
        recipe.setText(intent.getStringExtra(MealViewHolder.RECIPE));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String snackBarMessage;
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
