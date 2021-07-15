package com.project.segunfrancis.chopwell.presentation.utils

import android.content.Context
import android.graphics.Color
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.project.segunfrancis.chopwell.entity.MealEntity
import com.project.segunfrancis.usecase.model.MealUC

fun ImageView.loadImage(url: Any) {
    Glide.with(this).load(url).placeholder(circularProgressDrawable(this.context)).into(this)
}

fun circularProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 6.0f
        setColorSchemeColors(Color.WHITE, Color.GREEN, Color.rgb(216, 27, 96))
        centerRadius = 30.0f
        start()
    }
}

fun <T>MutableLiveData<T>.toLiveData(): LiveData<T> {
    return this
}

fun MealUC.toMealEntity(): MealEntity {
    return MealEntity(id, category, mealName, imageURL, description, preparation, recipe, queryMealName)
}
