package com.example.computer.chopwell;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.computer.chopwell.adapter.MealAdapter;
import com.example.computer.chopwell.model.MealModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BeveragesActivity extends AppCompatActivity {

    private DatabaseReference myRef;
    private List<MealModel> modelList;
    private MealAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beverages);

        modelList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view_beverages);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(BeveragesActivity.this));

        myRef = FirebaseDatabase.getInstance().getReference("meals");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    MealModel mealModel = snapshot.getValue(MealModel.class);
                    if (mealModel.getCategory().equals("Beverages")) {
                        modelList.add(mealModel);
                    }
                }
                adapter = new MealAdapter(BeveragesActivity.this, modelList);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
