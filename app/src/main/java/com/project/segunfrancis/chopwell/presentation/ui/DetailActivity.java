package com.project.segunfrancis.chopwell.presentation.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.project.segunfrancis.chopwell.R;
import com.project.segunfrancis.chopwell.entity.MealEntity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.project.segunfrancis.chopwell.presentation.utils.Utility.MEAL_ADAPTER_TO_DETAIL_ACTIVITY;

public class DetailActivity extends AppCompatActivity {

    private ExtendedFloatingActionButton fab;
    private DatabaseReference myRef;
    private MealEntity mealEntity;
    private final String TAG = DetailActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        final CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(DetailActivity.this);
        circularProgressDrawable.setStrokeWidth(15.0f);
        circularProgressDrawable.setColorSchemeColors(Color.GREEN, Color.rgb(168, 187, 208));
        circularProgressDrawable.setCenterRadius(45.0f);
        circularProgressDrawable.start();

        // Retrieving data from intent
        final Intent intent = getIntent();
        mealEntity = (MealEntity) intent.getSerializableExtra(MEAL_ADAPTER_TO_DETAIL_ACTIVITY);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(mealEntity.getMealName());
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

        Glide.with(DetailActivity.this)
                .load(mealEntity.getImageURL())
                .placeholder(circularProgressDrawable)
                .error(R.drawable.error_image)
                .into(imageView);

        description.setText(mealEntity.getDescription());
        preparation.setText(mealEntity.getPreparation());
        recipe.setText(mealEntity.getRecipe());

        fab.setOnClickListener(view -> {
            String snackBarMessage;
            // Check if user is logged in
            if (mealEntity.getUserId() != null) {
                if (fabImage.getLevel() == 0) {
                    fabImage.setLevel(1);
                    snackBarMessage = "Added to Favorite";
                    Snackbar.make(view, snackBarMessage, Snackbar.LENGTH_SHORT)
                            .show();

                    // Add favorite to firebase
                    myRef.child("favorites").child(mealEntity.getUserId()).child(mealEntity.getId()).setValue(mealEntity);
                    // updateFavoritesList();
                } else if (fabImage.getLevel() == 1) {
                    fabImage.setLevel(0);
                    snackBarMessage = "Removed from Favorite";
                    Snackbar.make(view, snackBarMessage, Snackbar.LENGTH_SHORT)
                            .show();

                    // Remove favorite from firebase
                    myRef.child("favorites").child(mealEntity.getUserId()).child(mealEntity.getId()).removeValue();
                    // updateFavoritesList();
                }
            } else {
                snackBarMessage = "Sign in to use this feature";
                Snackbar.make(fab, snackBarMessage, Snackbar.LENGTH_LONG)
                        .setAction("SIGN IN", view1 -> navigateToSignInActivity())
                        .show();
            }
        });
    }

    private void setFavorite() {
        // Check if user is logged in
        if (mealEntity.getUserId() != null) {
            myRef.child("favorites").child(mealEntity.getUserId())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Drawable fabImage = fab.getIcon();

                            if (dataSnapshot.hasChild(mealEntity.getId())) {
                                fabImage.setLevel(1);
                            } else {
                                fabImage.setLevel(0);
                            }
                            Log.d(TAG, "child present: " + dataSnapshot.hasChild(mealEntity.getId()));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d(TAG, databaseError.getMessage());
                        }
                    });
        }
    }

    private void navigateToSignInActivity() {
        startActivity(new Intent(DetailActivity.this, StartActivity.class));
    }
}
