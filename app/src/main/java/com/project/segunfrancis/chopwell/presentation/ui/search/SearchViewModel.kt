package com.project.segunfrancis.chopwell.presentation.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.segunfrancis.chopwell.entity.MealEntity
import com.project.segunfrancis.chopwell.presentation.utils.Result
import com.project.segunfrancis.chopwell.presentation.utils.toLiveData
import com.project.segunfrancis.chopwell.presentation.utils.toMealEntity
import com.project.segunfrancis.usecase.meal_usecase.SearchMealUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class SearchViewModel(private val searchMealUseCase: SearchMealUseCase) : ViewModel() {

    private val _searchMeal = MutableLiveData<Result<List<MealEntity?>?>>()
    val searchMeal get() = _searchMeal.toLiveData()

    fun searchMeal(query: String?) = viewModelScope.launch {
        searchMealUseCase(query)
            .onStart { _searchMeal.postValue(Result.Loading) }
            .catch { _searchMeal.postValue(Result.Error(it)) }
            .collect { _searchMeal.postValue(Result.Success(it?.map { meal -> meal?.toMealEntity() })) }
    }
}
