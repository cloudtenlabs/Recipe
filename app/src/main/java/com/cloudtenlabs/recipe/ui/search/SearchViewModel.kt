package com.cloudtenlabs.recipe.ui.search

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cloudtenlabs.recipe.model.meal.MealModel
import com.cloudtenlabs.recipe.repository.RecipeRepository
import com.cloudtenlabs.recipe.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: RecipeRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _searchedMovieList = MutableLiveData<Resource<MealModel>>()
    val searchedMealList: LiveData<Resource<MealModel>>
        get() = _searchedMovieList

    fun searchForMeal(searchQuery: String) {
        if (searchQuery.isEmpty()) {
            return
        }

        _searchedMovieList.value = Resource.Loading()
        viewModelScope.launch {
            val response = repository.searchMealWithName(searchQuery)
            _searchedMovieList.value = response
        }
    }

    fun changeSharedPreferencesValue(mealId: Int) {
        sharedPreferences.edit().putInt("mealId", mealId).apply()
    }
}