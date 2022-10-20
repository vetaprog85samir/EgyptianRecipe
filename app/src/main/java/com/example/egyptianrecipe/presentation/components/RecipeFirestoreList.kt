package com.example.egyptianrecipe.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.findNavController
import com.example.egyptianrecipe.domain.model.RecipeFirestore
import com.example.egyptianrecipe.domain.util.DataOrException
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun RecipeFirestoreList(
    loading: Boolean,
    recipes: DataOrException<List<RecipeFirestore>, Exception> =
        DataOrException(
            listOf(),
            Exception("")
        ),
    onNavigateToRecipeDetailScreen: (recipe: RecipeFirestore) -> Unit
){
    Box(modifier = Modifier
        .background(color = MaterialTheme.colors.surface)
    ) {
        if (loading && recipes==null) {
            HorizontalDottedProgressBar()
        }
        else if(recipes==null){
            NothingHere()
        }
        else {
            recipes?.let {
                LazyColumn {
                    items(
                        items = recipes.data.orEmpty()
                    ) { recipe ->
                        RecipeFirestoreCardEdited(
                            recipe = recipe,
                            onClick = {
                                onNavigateToRecipeDetailScreen(recipe)

                            }
                        )
                    }
               }
            }

            val e = recipes.e
            e?.let {
                Text(
                    text = e.message!!,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}







