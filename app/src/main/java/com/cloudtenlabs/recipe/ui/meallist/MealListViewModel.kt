package com.cloudtenlabs.recipe.ui.meallist

import android.content.SharedPreferences
import androidx.lifecycle.*
import com.cloudtenlabs.recipe.model.meal.MealModel
import com.cloudtenlabs.recipe.repository.RecipeRepository
import com.cloudtenlabs.recipe.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealListViewModel @Inject constructor(

    private val repository: RecipeRepository,
    savedStateHandle: SavedStateHandle,
    private val sharedPreferences: SharedPreferences

) : ViewModel() {
    private val categoryName: String? = savedStateHandle["categoryName"]
    private val countryOrCategory: Int? = savedStateHandle["countryOrCategory"]

    private val _mealList = MutableLiveData<Resource<MealModel>>()
    val mealList: LiveData<Resource<MealModel>>
        get() = _mealList

    init {
        getMealList()
    }

    fun getMealList() {
        _mealList.value = Resource.Loading()
        viewModelScope.launch {

            _mealList.value = categoryName?.let {
                if (countryOrCategory == 0) {
                    repository.filterByCategory(it)
                } else {
                    repository.filterByNation(it)
                }
            }
        }
    }

    fun changeSharedPreferencesValue(mealId: Int) {
        sharedPreferences.edit().putInt("mealId", mealId).apply()
    }
}