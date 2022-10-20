package com.example.egyptianrecipe.presentation.ui.recipe


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.egyptianrecipe.datastore.SettingsDataStore
import com.example.egyptianrecipe.domain.model.RecipeFirestore
import com.example.egyptianrecipe.presentation.components.RecipeViewFirestore
import com.example.egyptianrecipe.presentation.theme.AppTheme
import com.example.egyptianrecipe.presentation.ui.recipe_list.RecipeListFirestoreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipeFirestoreFragment : Fragment() {

    @Inject
    lateinit var dataStore: SettingsDataStore

    private val viewModel: RecipeListFirestoreViewModel by viewModels()

    private val args : RecipeFirestoreFragmentArgs by navArgs()

    val recipe: RecipeFirestore? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val recipe = args.recipe

                val loading = viewModel.loading.value

                val scaffoldState = rememberScaffoldState()

                AppTheme(
                    displayProgressBar = loading,
                    scaffoldState = scaffoldState,
                    darkTheme = dataStore.isDark.value
                ) {

                    Scaffold {
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            recipe?.let {
                                RecipeViewFirestore(recipe = it)

                            }
                        }
                    }
                }
            }
        }
    }
}