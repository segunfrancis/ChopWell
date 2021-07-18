package com.project.segunfrancis.chopwell.presentation.ui;

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
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.project.segunfrancis.chopwell.R;
import com.project.segunfrancis.chopwell.adapter.MealAdapter;
import com.project.segunfrancis.chopwell.entity.MealEntity;
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

import static com.project.segunfrancis.chopwell.presentation.utils.Utility.CATEGORY_INTENT_KEY;

public class CategoryActivity extends AppCompatActivity {

    private List<MealEntity> modelList;
    private MealAdapter searchAdapter;
    private RecyclerView searchResults;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // Creation of the CircularProgressDrawable
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(CategoryActivity.this);
        circularProgressDrawable.setStrokeWidth(6.0f);
        circularProgressDrawable.setColorSchemeColors(Color.WHITE, Color.GREEN, Color.rgb(216, 27, 96));
        circularProgressDrawable.setCenterRadius(50.0f);
        circularProgressDrawable.start();

        mAuth = FirebaseAuth.getInstance();

        //searchResults = findViewById(R.id.search_recycler_view);
        searchResults.setHasFixedSize(true);
        searchResults.setLayoutManager(new LinearLayoutManager(CategoryActivity.this));

        modelList = new ArrayList<>();
    }

    private void loadImages(Context context, int imageId, ImageView view) {
        Glide.with(context).load(imageId).into(view);
    }

    public void openMealListActivity(String category) {
        startActivity(new Intent(CategoryActivity.this, MealListActivity.class).putExtra(CATEGORY_INTENT_KEY, category));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

                            MealEntity model = snapshot.getValue(MealEntity.class);
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
            invalidateOptionsMenu();
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.action_favorites) {
            if (mAuth.getUid() != null) {
                startActivity(new Intent(CategoryActivity.this, FavoritesActivity.class));
            } else {
                Snackbar.make(findViewById(R.id.root), "Sign in to use this feature", Snackbar.LENGTH_LONG)
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
                .setIcon(R.drawable.ic_app_icon)
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
