package com.example.egyptianrecipe.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecipeFirestore(
    var id: Int? = null,
    var title: String? = null,
    var rating: Int? = null,
    var ingredients: String? = null,
    var img: String? = null
) : Parcelable
