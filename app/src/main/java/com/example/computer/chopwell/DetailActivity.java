package com.example.computer.chopwell;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.computer.chopwell.adapter.MealAdapter.MealViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailActivity extends AppCompatActivity {

    private ExtendedFloatingActionButton fab;
    private String itemId;
    private String mealNameString;
    private String userId;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        final CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(DetailActivity.this);
        circularProgressDrawable.setStrokeWidth(15.0f);
        circularProgressDrawable.setColorSchemeColors(Color.RED, Color.GREEN, Color.rgb(168, 187, 208));
        circularProgressDrawable.setCenterRadius(45.0f);
        circularProgressDrawable.start();

        final Intent intent = getIntent();

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(intent.getStringExtra(MealViewHolder.MEAL_NAME));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        fab = findViewById(R.id.fab);
        ImageView imageView = findViewById(R.id.meal_image_large);
        TextView description = findViewById(R.id.description_large);
        TextView preparation = findViewById(R.id.preparation_large);
        TextView recipe = findViewById(R.id.recipe_large);

        final Drawable fabImage = fab.getIcon();

        // Setting the favorite items of a user
        setFavorite();

        // Retrieving data from intent
        itemId = intent.getStringExtra(MealViewHolder.ID);
        userId = intent.getStringExtra(MealViewHolder.USERID);
        mealNameString = intent.getStringExtra(MealViewHolder.MEAL_NAME);
        String descriptionString = intent.getStringExtra(MealViewHolder.DESCRIPTION);
        String preparationString = intent.getStringExtra(MealViewHolder.PREPARATION);
        String recipeString = intent.getStringExtra(MealViewHolder.RECIPE);
        String imageURLString = intent.getStringExtra(MealViewHolder.IMAGE_URL);

        Glide.with(DetailActivity.this)
                .load(imageURLString)
                .placeholder(circularProgressDrawable)
                .error(R.drawable.error_image)
                .into(imageView);

        description.setText(descriptionString);
        preparation.setText(preparationString);
        recipe.setText(recipeString);

        fab.setOnClickListener(view -> {
            String snackBarMessage;
            // Check if user is logged in
            if (userId != null) {
                if (fabImage.getLevel() == 0) {
                    fabImage.setLevel(1);
                    snackBarMessage = "Added to Favorite";
                    Snackbar.make(view, snackBarMessage, Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();

                    // Add favorite to firebase
                    myRef.child("favorites").child(userId).child(itemId).setValue(itemId);
                } else if (fabImage.getLevel() == 1) {
                    fabImage.setLevel(0);
                    snackBarMessage = "Removed from Favorite";
                    Snackbar.make(view, snackBarMessage, Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();

                    // Remove favorite from firebase
                    myRef.child("favorites").child(userId).child(itemId).removeValue();
                }
            } else {
                Toast.makeText(DetailActivity.this, "You have to be singed in to use this feature", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setFavorite();
    }

    private void setFavorite() {
        userId = getIntent().getStringExtra(MealViewHolder.USERID);
        itemId = getIntent().getStringExtra(MealViewHolder.ID);
        // Check if user is logged in
        if (userId != null) {
            myRef.child("favorites").child(userId).child(itemId)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Drawable fabImage = fab.getIcon();
                            String mealId = dataSnapshot.getValue(String.class);
                            // Check if itemId is present in database
                            if (TextUtils.equals(mealId, itemId)) {
                                fabImage.setLevel(1);
                            } else {
                                fabImage.setLevel(0);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(DetailActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
