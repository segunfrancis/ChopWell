package com.project.segunfrancis.chopwell.adapter;

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
import com.project.segunfrancis.chopwell.presentation.ui.DetailActivity;
import com.project.segunfrancis.chopwell.R;
import com.project.segunfrancis.chopwell.entity.MealEntity;

import java.util.List;

import static com.project.segunfrancis.chopwell.presentation.utils.Utility.MEAL_ADAPTER_TO_DETAIL_ACTIVITY;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.MealViewHolder> {
    private Context context;
    private List<MealEntity> modelList;

    public FavoritesAdapter(Context context, List<MealEntity> modelList) {
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

        // Using Glide to load images
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

        MealViewHolder(@NonNull View itemView) {
            super(itemView);

            mealName = itemView.findViewById(R.id.meal_name_fav);
            mealDescription = itemView.findViewById(R.id.meal_description_fav);
            mealImage = itemView.findViewById(R.id.meal_image_fav);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();

            Intent intent = new Intent(context.getApplicationContext(), DetailActivity.class);
            intent.putExtra(MEAL_ADAPTER_TO_DETAIL_ACTIVITY, modelList.get(position));
            context.startActivity(intent);
        }
    }
}
