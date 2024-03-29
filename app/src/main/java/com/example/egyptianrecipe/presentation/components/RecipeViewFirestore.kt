package com.example.egyptianrecipe.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.egyptianrecipe.domain.model.RecipeFirestore
import com.example.egyptianrecipe.util.DEFAULT_RECIPE_IMAGE
import com.example.egyptianrecipe.util.loadFirestorePicture
import kotlinx.coroutines.ExperimentalCoroutinesApi

const val IMAGE_HEIGHT = 260

@ExperimentalCoroutinesApi
@Composable
fun RecipeViewFirestore(
    recipe: RecipeFirestore,
){
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        item {
            val image = loadFirestorePicture(url = recipe.img, defaultImage = DEFAULT_RECIPE_IMAGE).value
            image?.let { img ->
                Image(
                    bitmap = img.asImageBitmap(),
                    contentDescription = "Recipe Featured Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IMAGE_HEIGHT.dp)
                    ,
                    contentScale = ContentScale.Crop,
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                ){
                    recipe.title?.let { title ->
                        Text(
                            text = title,
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .wrapContentWidth(Alignment.Start)
                            ,
                            style = MaterialTheme.typography.h3
                        )
                    }

                    val rank = recipe.rating.toString()
                    Text(
                        text = rank,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.End)
                            .align(Alignment.CenterVertically)
                        ,
                        style = MaterialTheme.typography.h5
                    )
                }

                recipe.ingredients?.let { ingredients ->

                        Text(
                            text = ingredients,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp)
                            ,
                            style = MaterialTheme.typography.body1
                        )


                }

            }
        }
    }
}