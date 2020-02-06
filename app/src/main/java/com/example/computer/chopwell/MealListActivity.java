package com.example.computer.chopwell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.computer.chopwell.adapter.MealAdapter;
import com.example.computer.chopwell.model.MealModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.computer.chopwell.utils.Utility.CATEGORY_INTENT_KEY;

public class MealListActivity extends AppCompatActivity {

    private List<MealModel> meatList, puddingList, soupList, breakfastList, snackList, beverageList, entreeList, sideDishList;
    private MealAdapter adapter;
    private RecyclerView recyclerView;
    private final static String TAG = MealListActivity.class.getSimpleName();
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list);

        Intent intent = getIntent();
        category = intent.getStringExtra(CATEGORY_INTENT_KEY);

        meatList = new ArrayList<>();
        puddingList = new ArrayList<>();
        soupList = new ArrayList<>();
        breakfastList = new ArrayList<>();
        snackList = new ArrayList<>();
        beverageList = new ArrayList<>();
        entreeList = new ArrayList<>();
        sideDishList = new ArrayList<>();

        recyclerView = findViewById(R.id.meal_list_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MealListActivity.this));

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("meals");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MealModel mealModel = snapshot.getValue(MealModel.class);
                    switch (mealModel.getCategory()) {
                        case "Entrees": {
                            entreeList.add(mealModel);
                            break;
                        }
                        case "Breakfast": {
                            breakfastList.add(mealModel);
                            break;
                        }
                        case "Beverages": {
                            beverageList.add(mealModel);
                            break;
                        }
                        case "Meat": {
                            meatList.add(mealModel);
                            break;
                        }
                        case "Snacks": {
                            snackList.add(mealModel);
                            break;
                        }
                        case "Side Dishes": {
                            sideDishList.add(mealModel);
                            break;
                        }
                        case "Soups and Stews": {
                            soupList.add(mealModel);
                            break;
                        }
                        case "Puddings": {
                            puddingList.add(mealModel);
                            break;
                        }
                    }
                }
                switch (category) {
                    case "Beverages":
                        adapter = new MealAdapter(MealListActivity.this, beverageList);
                        setActionBarTitle("Beverages");
                        break;
                    case "Entrees":
                        adapter = new MealAdapter(MealListActivity.this, entreeList);
                        setActionBarTitle("Entrees");
                        break;
                    case "Meat":
                        adapter = new MealAdapter(MealListActivity.this, meatList);
                        setActionBarTitle("Meat");
                        break;
                    case "Snacks":
                        adapter = new MealAdapter(MealListActivity.this, snackList);
                        setActionBarTitle("Snacks");
                        break;
                    case "Side Dishes":
                        adapter = new MealAdapter(MealListActivity.this, sideDishList);
                        setActionBarTitle("Side Dishes");
                        break;
                    case "Soups and Stews":
                        adapter = new MealAdapter(MealListActivity.this, soupList);
                        setActionBarTitle("Soups and Stews");
                        break;
                    case "Puddings":
                        adapter = new MealAdapter(MealListActivity.this, puddingList);
                        setActionBarTitle("Puddings");
                        break;
                    case "Breakfast":
                        adapter = new MealAdapter(MealListActivity.this, breakfastList);
                        setActionBarTitle("Breakfast");
                        break;
                }
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "Database Error: " + databaseError.getMessage());
            }
        });
    }

    private void setActionBarTitle(String actionBarTitle) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(actionBarTitle);
    }
}
