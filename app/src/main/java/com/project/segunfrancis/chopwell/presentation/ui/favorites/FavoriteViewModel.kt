package com.project.segunfrancis.chopwell.presentation.ui.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.segunfrancis.chopwell.entity.MealEntity
import com.project.segunfrancis.chopwell.presentation.utils.Result
import com.project.segunfrancis.chopwell.presentation.utils.toLiveData
import com.project.segunfrancis.chopwell.presentation.utils.toMealEntity
import com.project.segunfrancis.usecase.meal_usecase.GetAllFavoritesUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class FavoriteViewModel(private val allFavoritesUseCase: GetAllFavoritesUseCase) : ViewModel() {

    private val _allFavorites = MutableLiveData<Result<List<MealEntity?>>>()
    val allFavorites = _allFavorites.toLiveData()

    init {
        viewModelScope.launch {
            allFavoritesUseCase()
                .onStart { _allFavorites.postValue(Result.Loading) }
                .catch { _allFavorites.postValue(Result.Error(it)) }
                .collect { _allFavorites.postValue(Result.Success(it?.map { meal -> meal?.toMealEntity() })) }
        }
    }
}
