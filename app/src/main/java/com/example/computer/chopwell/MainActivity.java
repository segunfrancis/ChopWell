package com.example.computer.chopwell;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.computer.chopwell.model.MealModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private DatabaseReference myRef;
    private EditText mealName, imageURL, description, preparation, recipe;
    private Spinner category;
    private Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        update.setOnClickListener(v -> {

            String[] categoryItems = getResources().getStringArray(R.array.category);
            int position = category.getSelectedItemPosition();
            final String categoryString = categoryItems[position];
            final String nameString = mealName.getText().toString().trim();
            final String imageString = imageURL.getText().toString().trim();
            final String descriptionString = description.getText().toString().trim();
            final String preparationString = preparation.getText().toString().trim();
            final String recipeString = recipe.getText().toString().trim();

            if (TextUtils.isEmpty(nameString) || TextUtils.isEmpty(imageString) ||
                    TextUtils.isEmpty(descriptionString) || TextUtils.isEmpty(preparationString) ||
                    TextUtils.isEmpty(recipeString)) {
                Toast.makeText(MainActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            } else {
                String key = myRef.child("meals").push().getKey();
                MealModel model = new MealModel(key, categoryString, nameString, imageString,
                        descriptionString, preparationString, recipeString, nameString.toLowerCase());
                myRef.child("meals").child(key).setValue(model)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        openNextActivity(v);
                    } else {
                        Toast.makeText(MainActivity.this, "Unable to add meal", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, task.getException().getLocalizedMessage());
                    }
                });
            }
        });
    }

    private void initViews() {
        category = findViewById(R.id.category);
        mealName = findViewById(R.id.meal_name);
        imageURL = findViewById(R.id.image_url);
        description = findViewById(R.id.description);
        preparation = findViewById(R.id.preparation);
        recipe = findViewById(R.id.recipe);
        update = findViewById(R.id.post);
    }

    public void openNextActivity(View view) {
        startActivity(new Intent(MainActivity.this, CategoryActivity.class));
    }
}
