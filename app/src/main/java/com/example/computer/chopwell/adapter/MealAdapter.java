package com.example.computer.chopwell.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.computer.chopwell.DetailActivity;
import com.example.computer.chopwell.R;
import com.example.computer.chopwell.model.MealModel;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {
    private Context context;
    private List<MealModel> modelList;

    public MealAdapter(Context context, List<MealModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.meal_list, viewGroup, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder mealViewHolder, int position) {
        mealViewHolder.mealName.setText(modelList.get(position).getMealName());
        mealViewHolder.mealDescription.setText(modelList.get(position).getDescription());

        // Creation of the CircularProgressDrawable
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context.getApplicationContext());
        circularProgressDrawable.setStrokeWidth(10.0f);
        circularProgressDrawable.setColorSchemeColors(Color.WHITE, Color.GREEN, Color.rgb(216, 27, 96));
        circularProgressDrawable.setCenterRadius(20.0f);
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

        public TextView mealName, mealDescription;
        public ImageView mealImage;

        public static final String MEAL_NAME = "name";
        public static final String IMAGE_URL = "URL";
        public static final String DESCRIPTION = "description";
        public static final String PREPARATION = "preparation";
        public static final String RECIPE = "recipe";

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);

            mealName = itemView.findViewById(R.id.meal_name);
            mealDescription = itemView.findViewById(R.id.meal_description);
            mealImage = itemView.findViewById(R.id.meal_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String mealName, imageURL, description, preparation, recipe, itemId;
            int position = getLayoutPosition();

            mealName = modelList.get(position).getMealName();
            imageURL = modelList.get(position).getImageURL();
            description = modelList.get(position).getDescription();
            preparation = modelList.get(position).getPreparation();
            recipe = modelList.get(position).getRecipe();
            itemId = modelList.get(position).getId();

            Intent intent = new Intent(context.getApplicationContext(), DetailActivity.class);
            intent.putExtra(MEAL_NAME, mealName);
            intent.putExtra(IMAGE_URL, imageURL);
            intent.putExtra(DESCRIPTION, description);
            intent.putExtra(PREPARATION, preparation);
            intent.putExtra(RECIPE, recipe);
            intent.putExtra("id", itemId);
            context.startActivity(intent);
        }
    }
}
