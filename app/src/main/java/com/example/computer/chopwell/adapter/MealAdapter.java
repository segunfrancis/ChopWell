package com.example.computer.chopwell.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.computer.chopwell.R;
import com.example.computer.chopwell.model.MealModel;

import java.util.List;
import java.util.zip.Inflater;

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
        // Use Glide
        Glide.with(context)
                .load(modelList.get(position).getImageURL())
                .placeholder(R.drawable.meal_placeholder)
                .into(mealViewHolder.mealImage);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MealViewHolder extends RecyclerView.ViewHolder {

        public TextView mealName, mealDescription;
        public ImageView mealImage;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);

            mealName = itemView.findViewById(R.id.meal_name);
            mealDescription = itemView.findViewById(R.id.meal_description);
            mealImage = itemView.findViewById(R.id.meal_image);
        }
    }
}
