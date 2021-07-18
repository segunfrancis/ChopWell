package com.project.segunfrancis.chopwell.presentation.ui.meal_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.segunfrancis.chopwell.R
import com.project.segunfrancis.chopwell.databinding.MealListBinding
import com.project.segunfrancis.chopwell.entity.MealEntity
import com.project.segunfrancis.chopwell.presentation.ui.util.ChopWellDiffUtil
import com.project.segunfrancis.chopwell.presentation.utils.loadImage

class MealAdapter : ListAdapter<MealEntity, MealAdapter.MealViewHolder>(ChopWellDiffUtil()){

    class MealViewHolder(private val binding: MealListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: MealEntity) = with(binding) {
            mealName.text = meal.mealName
            mealDescription.text = meal.description
            mealImage.loadImage(meal.imageURL)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.meal_list, parent, false)
        return MealViewHolder(MealListBinding.bind(view))
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
