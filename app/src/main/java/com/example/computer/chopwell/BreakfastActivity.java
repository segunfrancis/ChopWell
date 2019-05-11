package com.example.computer.chopwell;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.computer.chopwell.adapter.MealAdapter;
import com.example.computer.chopwell.model.MealModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BreakfastActivity extends AppCompatActivity {

    private DatabaseReference myRef;
    private List<MealModel> modelList;
    private MealAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast);

        modelList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view_breakfast);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(BreakfastActivity.this));

        myRef = FirebaseDatabase.getInstance().getReference("meals").child("Breakfast");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MealModel mealModel = snapshot.getValue(MealModel.class);
                    modelList.add(mealModel);
                }
                adapter = new MealAdapter(BreakfastActivity.this, modelList);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
