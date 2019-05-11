package com.example.computer.chopwell;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
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
}
