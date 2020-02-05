package com.example.computer.chopwell;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.computer.chopwell.adapter.MealAdapter;
import com.example.computer.chopwell.model.MealModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private List<MealModel> modelList;
    private MealAdapter searchAdapter;
    private RecyclerView searchResults;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        loadImages(this, R.drawable.chapman, findViewById(R.id.image_beverages));
        loadImages(this, R.drawable.breakfast_image, findViewById(R.id.image_breakfast));
        loadImages(this, R.drawable.entree_image, findViewById(R.id.image_entrees));
        loadImages(this, R.drawable.meat_image, findViewById(R.id.image_meats));
        loadImages(this, R.drawable.pudding_image, findViewById(R.id.image_puddings));
        loadImages(this, R.drawable.side_dish, findViewById(R.id.image_sideDishes));
        loadImages(this, R.drawable.snack_image, findViewById(R.id.image_snacks));
        loadImages(this, R.drawable.food_delivery, findViewById(R.id.image_soupsAndStews));

        // Creation of the CircularProgressDrawable
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(CategoryActivity.this);
        circularProgressDrawable.setStrokeWidth(6.0f);
        circularProgressDrawable.setColorSchemeColors(Color.WHITE, Color.GREEN, Color.rgb(216, 27, 96));
        circularProgressDrawable.setCenterRadius(50.0f);
        circularProgressDrawable.start();

        mAuthListener = firebaseAuth -> {
        };

        mAuth = FirebaseAuth.getInstance();

        searchResults = findViewById(R.id.search_recycler_view);
        searchResults.setHasFixedSize(true);
        searchResults.setLayoutManager(new LinearLayoutManager(CategoryActivity.this));

        modelList = new ArrayList<>();
    }

    private void loadImages(Context context, int imageId, ImageView view) {
        Glide.with(context).load(imageId).into(view);
    }

    public void openBeveragesActivity(View view) {
        startActivity(new Intent(CategoryActivity.this, BeveragesActivity.class));
    }

    public void openBreakfastActivity(View view) {
        startActivity(new Intent(CategoryActivity.this, BreakfastActivity.class));
    }

    public void openEntreesActivity(View view) {
        startActivity(new Intent(CategoryActivity.this, EntreesActivity.class));
    }

    public void openMeatsActivity(View view) {
        startActivity(new Intent(CategoryActivity.this, MeatActivity.class));
    }

    public void openPuddingsActivity(View view) {
        startActivity(new Intent(CategoryActivity.this, PuddingsActivity.class));
    }

    public void openSideDishesActivity(View view) {
        startActivity(new Intent(CategoryActivity.this, SideDishesActivity.class));
    }

    public void openSnacksActivity(View view) {
        startActivity(new Intent(CategoryActivity.this, SnacksActivity.class));
    }

    public void openSoupsActivity(View view) {
        startActivity(new Intent(CategoryActivity.this, SoupsActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem logout = menu.findItem(R.id.action_logout);
        // Implement SearchView
        MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint("Search meals");
        // Implement Search
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.length() == 0) {
                    s = " ";
                }
                Query query = FirebaseDatabase.getInstance().getReference("meals")
                        .orderByChild("queryMealName")
                        .startAt(s.toLowerCase())
                        .endAt(s.toLowerCase() + "\uf8ff");
                modelList.clear();

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            MealModel model = snapshot.getValue(MealModel.class);
                            modelList.add(model);
                        }
                        searchAdapter = new MealAdapter(CategoryActivity.this, modelList);
                        searchResults.setAdapter(searchAdapter);
                        searchAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        modelList.clear();
                    }
                });
                return false;
            }
        });
        if (mAuth.getUid() == null) {
            logout.setTitle(getString(R.string.sign_in));
            invalidateOptionsMenu();
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            if (item.getTitle().equals(getString(R.string.logout))) {
                new MaterialAlertDialogBuilder(CategoryActivity.this, R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
                        .setTitle("Chop Well")
                        .setIcon(R.drawable.ic_launcher_foreground)
                        .setMessage("Do you want to Logout?")
                        .setNegativeButton("NO", (dialog, which) -> dialog.dismiss())
                        .setPositiveButton("YES", (dialog, which) -> {
                            mAuth.signOut();
                            Snackbar.make(findViewById(R.id.root), "You have signed out successfully", Snackbar.LENGTH_INDEFINITE)
                                    .show();
                            recreate();
                        })
                        .create()
                        .show();
            } else if (item.getTitle().equals(getString(R.string.sign_in))) {
                startActivity(new Intent(CategoryActivity.this, StartActivity.class));
            }
        } else if (item.getItemId() == R.id.action_favorites) {
            if (mAuth.getUid() != null) {
                startActivity(new Intent(CategoryActivity.this, FavoritesActivity.class));
            } else {
                Snackbar.make(findViewById(R.id.root), "Sign in to use this feature", Snackbar.LENGTH_INDEFINITE)
                        .setAction("SIGN IN", view -> navigateToSignInActivity())
                        .show();
            }
        } else {
            int id = item.getItemId();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        new MaterialAlertDialogBuilder(CategoryActivity.this, R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
                .setTitle("Chop Well")
                .setIcon(R.drawable.ic_launcher_foreground)
                .setMessage("Do you want to exit?")
                .setPositiveButton("YES", (dialogInterface, i) -> {
                    System.exit(0);
                    dialogInterface.dismiss();
                })
                .setNegativeButton("NO", (dialogInterface, i) -> dialogInterface.dismiss())
                .create()
                .show();
    }

    private void navigateToSignInActivity() {
        startActivity(new Intent(CategoryActivity.this, StartActivity.class));
    }
}
