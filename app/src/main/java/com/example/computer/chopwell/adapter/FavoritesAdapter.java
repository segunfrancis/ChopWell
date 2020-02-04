package com.example.computer.chopwell.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.computer.chopwell.DetailActivity;
import com.example.computer.chopwell.R;
import com.example.computer.chopwell.model.MealModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.MealViewHolder> {
    private Context context;
    private List<MealModel> modelList;

    public FavoritesAdapter(Context context, List<MealModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.favorites_meal_list, viewGroup, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder mealViewHolder, int position) {
        mealViewHolder.mealName.setText(modelList.get(position).getMealName());
        mealViewHolder.mealDescription.setText(modelList.get(position).getDescription());

        // Creation of the CircularProgressDrawable
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context.getApplicationContext());
        circularProgressDrawable.setStrokeWidth(6.0f);
        circularProgressDrawable.setColorSchemeColors(Color.WHITE, Color.GREEN, Color.rgb(216, 27, 96));
        circularProgressDrawable.setCenterRadius(50.0f);
        circularProgressDrawable.start();

        // Use Glide
        Glide.with(context)
                .load(modelList.get(position).getImageURL())
                .placeholder(circularProgressDrawable)
                .error(R.drawable.error_image)
                .into(mealViewHolder.mealImage);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MealViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mealName, mealDescription;
        ImageView mealImage;

        static final String ID = "id";
        static final String MEAL_NAME = "name";
        static final String IMAGE_URL = "URL";
        static final String DESCRIPTION = "description";
        static final String PREPARATION = "preparation";
        static final String RECIPE = "recipe";
        static final String USERID = "userId";

        MealViewHolder(@NonNull View itemView) {
            super(itemView);

            mealName = itemView.findViewById(R.id.meal_name_fav);
            mealDescription = itemView.findViewById(R.id.meal_description_fav);
            mealImage = itemView.findViewById(R.id.meal_image_fav);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String mealName, imageURL, description, preparation, recipe, itemId, userId;
            int position = getLayoutPosition();
            FirebaseAuth mAuth = FirebaseAuth.getInstance();

            itemId = modelList.get(position).getId();
            mealName = modelList.get(position).getMealName();
            imageURL = modelList.get(position).getImageURL();
            description = modelList.get(position).getDescription();
            preparation = modelList.get(position).getPreparation();
            recipe = modelList.get(position).getRecipe();
            userId = mAuth.getUid();

            Intent intent = new Intent(context.getApplicationContext(), DetailActivity.class);
            intent.putExtra(ID, itemId);
            intent.putExtra(MEAL_NAME, mealName);
            intent.putExtra(IMAGE_URL, imageURL);
            intent.putExtra(DESCRIPTION, description);
            intent.putExtra(PREPARATION, preparation);
            intent.putExtra(RECIPE, recipe);
            intent.putExtra(USERID, userId);
            context.startActivity(intent);
        }
    }
}
