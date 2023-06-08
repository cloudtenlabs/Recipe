package com.cloudtenlabs.recipe.ui.details.ingredients

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cloudtenlabs.recipe.model.meal.MealModel
import com.cloudtenlabs.recipe.repository.RecipeRepository
import com.cloudtenlabs.recipe.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IngredientsViewModel @Inject constructor(
    private val repository: RecipeRepository,
    sharedPreferences: SharedPreferences,
) : ViewModel() {


    private val mealId = sharedPreferences.getInt("mealId", 57982)

    private val _mealDetails = MutableLiveData<Resource<MealModel>>()
    val mealDetails: LiveData<Resource<MealModel>>
        get() = _mealDetails

    val ingredientsList = mutableListOf<String>()

    init {

        getMealModel()

    }

    fun getMealModel() {

        _mealDetails.value = Resource.Loading()
        viewModelScope.launch {
            _mealDetails.value = repository.lookUpWithId(mealId)
            delay(1000)

            ingredientsList.add(_mealDetails.value!!.data!!.meals[0].strIngredient1!!)
            delay(1000)

            println(ingredientsList[0])
        }
    }


    fun addItemToListIfExist(item: String?, list: MutableList<String>) {
        if (item.isNullOrEmpty()) {
            return
        } else {
        list.add(item)
        }

    }


}




