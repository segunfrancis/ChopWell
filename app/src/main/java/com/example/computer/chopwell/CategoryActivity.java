package com.example.computer.chopwell;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.computer.chopwell.adapter.MealAdapter;
import com.example.computer.chopwell.model.MealModel;
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

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                /*if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(CategoryActivity.this, StartActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }*/
            }
        };

        mAuth = FirebaseAuth.getInstance();

        searchResults = findViewById(R.id.search_recycler_view);
        searchResults.setHasFixedSize(true);
        searchResults.setLayoutManager(new LinearLayoutManager(CategoryActivity.this));

        modelList = new ArrayList<>();
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
        // Implement SearchView
        MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(CategoryActivity.this);
            builder.setMessage("Do you want to Logout?")
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mAuth.signOut();
                }
            }).create();
            builder.show();
        } else if (item.getItemId() == R.id.action_favorites) {
            if (mAuth.getUid() != null) {
                startActivity(new Intent(CategoryActivity.this, FavoritesActivity.class));
            } else {
                Toast.makeText(CategoryActivity.this, "Sign in to use this feature", Toast.LENGTH_SHORT).show();
            }
        } else {
            int id = item.getItemId();
        }
        return super.onOptionsItemSelected(item);
    }
}
