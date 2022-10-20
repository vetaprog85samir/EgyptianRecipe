package com.example.egyptianrecipe.presentation.ui.recipe_list

import com.example.egyptianrecipe.presentation.ui.recipe_list.FoodCategory.*

enum class FoodCategory(val value: String){
    CHICKEN("فراخ"),
    BEEF("لحوم"),
    SOUP("شوربة"),
    DESSERT("حلويات"),
    FISH("أسماك"),
    COOKIES("معجنات"),
    SALAD("سلاطات"),
    BEANS("بقوليات"),
    DRINKS("مشروبات"),
    VEGETABLES("خضار"),
}

fun getAllFoodCategories(): List<FoodCategory>{
    return listOf(CHICKEN,BEEF, SOUP,DESSERT,FISH,COOKIES,SALAD,BEANS,DRINKS,VEGETABLES)
}

fun getFoodCategory(value: String): FoodCategory? {
    val map = FoodCategory.values().associateBy(FoodCategory::value)
    return map[value]
}