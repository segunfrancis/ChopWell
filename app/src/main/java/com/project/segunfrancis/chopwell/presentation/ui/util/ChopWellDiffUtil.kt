package com.project.segunfrancis.chopwell.presentation.ui.util

import androidx.recyclerview.widget.DiffUtil
import com.project.segunfrancis.chopwell.entity.MealEntity

class ChopWellDiffUtil : DiffUtil.ItemCallback<MealEntity>() {
    override fun areItemsTheSame(oldItem: MealEntity, newItem: MealEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MealEntity, newItem: MealEntity): Boolean {
        return oldItem == newItem
    }
}