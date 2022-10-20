package com.example.egyptianrecipe.presentation.ui.recipe

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.egyptianrecipe.domain.model.RecipeFirestore
import com.example.egyptianrecipe.domain.util.DataOrException
import com.example.egyptianrecipe.repository.FireStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeFirestoreViewModel
@Inject constructor(
    private val repository: FireStoreRepository

): ViewModel() {


    val recipe: MutableState<RecipeFirestore?> = mutableStateOf(null)

    val loading = mutableStateOf(false)

    val data: MutableState<DataOrException<MutableState<RecipeFirestore?>, Exception>> = mutableStateOf(
        DataOrException(
            recipe,
            Exception("")
        )
    )
}