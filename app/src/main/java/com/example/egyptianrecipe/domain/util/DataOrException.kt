package com.example.egyptianrecipe.domain.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class DataOrException<T, E : Exception?>(
    var data: T? = null,
    var e: E? = null
)


