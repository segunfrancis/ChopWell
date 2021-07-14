package com.project.segunfrancis.chopwell.presentation.ui;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.project.segunfrancis.chopwell.R;
import com.project.segunfrancis.chopwell.adapter.FavoritesAdapter;
import com.project.segunfrancis.chopwell.entity.MealEntity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private static final String TAG = FavoritesActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private FavoritesAdapter favoritesAdapter;
    private List<MealEntity> modelList;
    private Group emptyGroup;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        myRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        emptyGroup = findViewById(R.id.empty_group);
        emptyGroup.setVisibility(View.GONE);
        recyclerView = findViewById(R.id.favorites_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        modelList = new ArrayList<>();

        myRef.child("favorites").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modelList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MealEntity mealEntity = snapshot.getValue(MealEntity.class);
                    modelList.add(mealEntity);
                }
                // Set empty list icon
                if (modelList.size() < 1) {
                    emptyGroup.setVisibility(View.VISIBLE);
                } else {
                    emptyGroup.setVisibility(View.GONE);
                }
                invalidateOptionsMenu();
                favoritesAdapter = new FavoritesAdapter(FavoritesActivity.this, modelList);
                recyclerView.setAdapter(favoritesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });

        // Set empty list icon
        if (modelList.size() < 1) {
            emptyGroup.setVisibility(View.VISIBLE);
        } else {
            emptyGroup.setVisibility(View.GONE);
        }
        favoritesAdapter = new FavoritesAdapter(FavoritesActivity.this, modelList);
        recyclerView.setAdapter(favoritesAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favorites, menu);
        menu.findItem(R.id.clear_favorites).setEnabled(modelList.size() >= 1);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.clear_favorites) {
            displayDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearFavorites() {
        myRef.child("favorites").child(mAuth.getUid()).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Snackbar.make(findViewById(R.id.root_favorite), "Your favorite list has been cleared", Snackbar.LENGTH_LONG).show();
            } else {
                Log.d(TAG, "Database Error: " + task.getException().getLocalizedMessage());
            }
        });
    }

    private void displayDialog() {
        new MaterialAlertDialogBuilder(FavoritesActivity.this, R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
                .setTitle("Chop Well")
                .setIcon(R.drawable.ic_app_icon)
                .setMessage("Do you want to Clear favorite list?")
                .setPositiveButton("YES", (dialog, which) -> {
                    clearFavorites();
                    dialog.dismiss();
                    invalidateOptionsMenu();
                })
                .setNegativeButton("NO", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }
}
