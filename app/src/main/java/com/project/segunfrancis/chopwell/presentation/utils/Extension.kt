package com.project.segunfrancis.chopwell.presentation.utils

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.project.segunfrancis.chopwell.R
import com.project.segunfrancis.chopwell.entity.MealEntity
import com.project.segunfrancis.usecase.model.MealUC

fun ImageView.loadImage(url: String) {
    Glide.with(this).load(url).placeholder(circularProgressDrawable(this.context)).into(this)
}

fun ImageView.loadImage(@DrawableRes res: Int) {
    Glide.with(this).load(res).placeholder(circularProgressDrawable(this.context)).into(this)
}

fun circularProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 6.0f
        setColorSchemeColors(Color.WHITE, Color.GREEN, Color.rgb(216, 27, 96))
        centerRadius = 30.0f
        start()
    }
}

fun Fragment.navigateTo(@IdRes destination: Int) {
    findNavController().navigate(destination)
}

fun Fragment.navigateTo(directions: NavDirections) {
    findNavController().navigate(directions)
}

fun <T>MutableLiveData<T>.toLiveData(): LiveData<T> {
    return this
}

fun MealUC.toMealEntity(): MealEntity {
    return MealEntity(id, category, mealName, imageURL, description, preparation, recipe, queryMealName)
}

fun View.showSuccessMessage(message: String, context: Context) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    snackbar.setBackgroundTint(ContextCompat.getColor(context, R.color.green600))
    snackbar.show()
}

fun View.showErrorMessage(message: String, context: Context) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    snackbar.setBackgroundTint(ContextCompat.getColor(context, R.color.colorPrimary))
    snackbar.show()
}
