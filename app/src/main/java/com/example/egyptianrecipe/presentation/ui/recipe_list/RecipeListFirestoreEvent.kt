package com.example.egyptianrecipe.presentation.ui.recipe_list

sealed class RecipeListFirestoreEvent {

    object NewSearchEvent : RecipeListFirestoreEvent()

    object GetRecipesEvent : RecipeListFirestoreEvent()
}