package com.example.egyptianrecipe.presentation.ui.recipe_list


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.egyptianrecipe.datastore.SettingsDataStore
import com.example.egyptianrecipe.domain.model.RecipeFirestore
import com.example.egyptianrecipe.presentation.components.FirestoreSearchAppBar
import com.example.egyptianrecipe.presentation.components.RecipeFirestoreList
import com.example.egyptianrecipe.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipeListFirestoreFragment : Fragment() {

    @Inject
    lateinit var dataStore: SettingsDataStore

    private val viewModel: RecipeListFirestoreViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val dataOrException = viewModel.data.value

                val query = viewModel.query.value

                val selectedCategory = viewModel.selectedCategory.value

                val loading = viewModel.loading.value

                val scaffoldState = rememberScaffoldState()

                AppTheme(
                    displayProgressBar = loading,
                    scaffoldState = scaffoldState,
                    darkTheme = dataStore.isDark.value//application.isDark.value,
                ) {

                    Scaffold(
                        topBar = {
                            FirestoreSearchAppBar(
                                query = query,
                                onQueryChanged = viewModel::onQueryChanged,
                                onExecuteSearch = {
                                    viewModel.onTriggerEvent(
                                        RecipeListFirestoreEvent.NewSearchEvent
                                    )
                                },
                                categories = getAllFoodCategories(),
                                selectedCategory = selectedCategory,
                                onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                                onToggleTheme = dataStore::toggleTheme//application::toggleLightTheme
                            )
                        },
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        },

                        ) {


                        RecipeFirestoreList(
                            loading = loading,
                            recipes = dataOrException,
                            onNavigateToRecipeDetailScreen = { recipe ->
                                val recipe = RecipeFirestore(
                                    recipe.id,
                                    recipe.title,
                                    recipe.rating,
                                    recipe.ingredients,
                                    recipe.img
                                )
                                val action =
                                    RecipeListFirestoreFragmentDirections.viewRecipe(
                                        recipe = recipe
                                    )

                                findNavController().navigate(action)

                            }
                        )

                    }
                }
            }

        }

    }

}