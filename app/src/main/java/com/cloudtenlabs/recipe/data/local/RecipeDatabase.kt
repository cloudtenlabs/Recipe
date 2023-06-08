package com.cloudtenlabs.recipe.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cloudtenlabs.recipe.model.meal.Meal

@Database(entities = [Meal::class],version = 1)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao (): RecipesDao

}
