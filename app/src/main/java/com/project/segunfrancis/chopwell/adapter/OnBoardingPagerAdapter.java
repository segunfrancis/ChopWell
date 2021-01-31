package com.project.segunfrancis.chopwell.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.segunfrancis.chopwell.R;
import com.project.segunfrancis.chopwell.entity.OnBoardingEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by SegunFrancis
 */

public class OnBoardingPagerAdapter extends RecyclerView.Adapter<OnBoardingPagerAdapter.OnBoardingViewHolder> {

    private List<OnBoardingEntity> mList;

    public OnBoardingPagerAdapter(List<OnBoardingEntity> list) {
        mList = list;
    }

    @NonNull
    @Override
    public OnBoardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnBoardingViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.onboarding_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OnBoardingViewHolder holder, int position) {
        holder.onBoardingImageView.setImageResource(mList.get(position).onBoardingImage);
        holder.onBoardingTextView.setText(mList.get(position).onBoardingText);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class OnBoardingViewHolder extends RecyclerView.ViewHolder {

        ImageView onBoardingImageView;
        TextView onBoardingTextView;

        OnBoardingViewHolder(@NonNull View itemView) {
            super(itemView);

            onBoardingImageView = itemView.findViewById(R.id.onboarding_image);
            onBoardingTextView = itemView.findViewById(R.id.onboarding_text);
        }
    }
}
