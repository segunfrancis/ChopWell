package com.project.segunfrancis.chopwell.presentation.ui.meal_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.segunfrancis.chopwell.entity.MealEntity
import com.project.segunfrancis.chopwell.presentation.utils.Result
import com.project.segunfrancis.chopwell.presentation.utils.toLiveData
import com.project.segunfrancis.chopwell.presentation.utils.toMealEntity
import com.project.segunfrancis.usecase.meal_usecase.GetAllMealsUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber

class MealListViewModel(private val getAllMealsUseCase: GetAllMealsUseCase) : ViewModel() {

    private val _allMeals = MutableLiveData<Result<List<MealEntity?>?>>()
    val allMeals = _allMeals.toLiveData()

    fun getAllMeals(category: String) = viewModelScope.launch {
        getAllMealsUseCase(category)
            .onStart { _allMeals.postValue(Result.Loading) }
            .catch { _allMeals.postValue(Result.Error(it)) }
            .collect { _allMeals.postValue(Result.Success(it?.map { meal -> meal?.toMealEntity() })) }
    }
}
