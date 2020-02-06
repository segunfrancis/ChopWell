package com.example.computer.chopwell;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import com.example.computer.chopwell.adapter.FavoritesAdapter;
import com.example.computer.chopwell.model.MealModel;
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
    private List<MealModel> modelList;
    private Group emptyGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        emptyGroup = findViewById(R.id.empty_group);
        emptyGroup.setVisibility(View.GONE);
        recyclerView = findViewById(R.id.favorites_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        modelList = new ArrayList<>();

        myRef.child("favorites").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modelList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MealModel mealModel = snapshot.getValue(MealModel.class);
                    modelList.add(mealModel);
                }
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
}
