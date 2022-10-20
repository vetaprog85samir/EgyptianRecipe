package com.example.egyptianrecipe.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.egyptianrecipe.domain.model.RecipeFirestore
import com.example.egyptianrecipe.domain.util.DataOrException
import com.example.egyptianrecipe.repository.FireStoreRepository
import com.example.egyptianrecipe.util.TAG
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

const val STATE_KEY_QUERY = "recipe.state.query.key"
const val STATE_KEY_RECIPES = "recipe.state.recipes.key"
const val STATE_KEY_SELECTED_CATEGORY = "recipe.state.query.selected_category"

@HiltViewModel
class RecipeListFirestoreViewModel
@Inject constructor(
    private val repository: FireStoreRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    val query = mutableStateOf("")

    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)

    var loading = mutableStateOf(false)
    val data: MutableState<DataOrException<List<RecipeFirestore>, Exception>> = mutableStateOf(
        DataOrException(
            listOf(),
            Exception("")
        )
    )

    init {
        savedStateHandle.get<String>(STATE_KEY_QUERY)?.let { q ->
            setQuery(q)
        }

        onTriggerEvent(RecipeListFirestoreEvent.GetRecipesEvent)
    }

    fun onTriggerEvent(event: RecipeListFirestoreEvent){
        viewModelScope.launch {
            try {
                when(event){

                    is RecipeListFirestoreEvent.GetRecipesEvent -> {
                        getRecipes()
                    }

                    is RecipeListFirestoreEvent.NewSearchEvent -> {
                        newSearch()
                    }

                }
            }catch (e: Exception){
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
            finally {
                Log.d(TAG, "launchJob: finally called.")
            }
        }
    }

    private fun getRecipes() {
        viewModelScope.launch {
            loading.value = true
            data.value = repository.getRecipesFromFirestore()
            loading.value = false
        }
    }

    private suspend fun newSearch() {
        loading.value = true

        data.value = DataOrException(listOf(), Exception(""))

        delay(2000)

        val result = repository.searchFirestore(

            query = query.value
        )
        data.value = result

        loading.value = false
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category)
        setSelectedCategory(newCategory)
        onQueryChanged(category)
    }

    private fun setSelectedCategory(category: FoodCategory?){
        selectedCategory.value = category
        savedStateHandle.set(STATE_KEY_SELECTED_CATEGORY, category)
    }

    fun onQueryChanged(query: String) {
        setQuery(query)
    }

    private fun setQuery(query: String){
        this.query.value = query
        savedStateHandle.set(STATE_KEY_QUERY, query)
    }

}