package com.example.computer.chopwell;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

        final String[] valueId = {null};
        final List<String> temp = new ArrayList<>();

        myRef.child("favorites").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    valueId[0] = snapshot.getValue(String.class);
                    for (String value : valueId) {
                        temp.add(value);
                        Log.i(TAG, value);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FavoritesActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        modelList = new ArrayList<>();

        myRef.child("meals").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String mealId = snapshot.getKey();
                    for (String favId : temp) {
                        if (TextUtils.equals(mealId, favId)) {
                            MealModel favModel = snapshot.getValue(MealModel.class);
                            modelList.add(favModel);
                        }
                    }
                    if (modelList.size() < 1) {
                        emptyGroup.setVisibility(View.VISIBLE);
                    } else {
                        emptyGroup.setVisibility(View.GONE);
                    }
                    favoritesAdapter = new FavoritesAdapter(FavoritesActivity.this, modelList);
                    recyclerView.setAdapter(favoritesAdapter);
                    favoritesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i(TAG, databaseError.getMessage());
            }
        });
    }
}
