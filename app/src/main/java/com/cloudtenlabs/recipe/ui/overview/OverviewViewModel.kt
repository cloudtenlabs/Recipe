package com.cloudtenlabs.recipe.ui.overview

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cloudtenlabs.recipe.mock.CountryMockData
import com.cloudtenlabs.recipe.model.categories.Categories
import com.cloudtenlabs.recipe.model.country.CountryModel
import com.cloudtenlabs.recipe.model.meal.MealModel
import com.cloudtenlabs.recipe.repository.RecipeRepository
import com.cloudtenlabs.recipe.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val repository: RecipeRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _categories = MutableLiveData<Resource<Categories>>()
    val categories: LiveData<Resource<Categories>>
        get() = _categories


    private val _randomMeal = MutableLiveData<Resource<MealModel>>()
    val randomMeal: LiveData<Resource<MealModel>>
        get() = _randomMeal

    val countryList: List<CountryModel>
        get() = CountryMockData.getCountries()

    init {
        getCategories()

    }

    private fun getCategories() {

        _categories.value = Resource.Loading(null)
        viewModelScope.launch {
            _categories.value = repository.categories()
        }
    }
    fun getRandomMeal(){
        _randomMeal.value= Resource.Loading(null)
        viewModelScope.launch {
            _randomMeal.value = repository.randomMeal()
        }
    }


    fun changeSharedPreferencesValue(mealId: Int) {
        sharedPreferences.edit().putInt("mealId", mealId).apply()
    }


}