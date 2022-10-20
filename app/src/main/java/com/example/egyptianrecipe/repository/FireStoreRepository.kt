package com.example.egyptianrecipe.repository

import androidx.compose.runtime.MutableState
import com.example.egyptianrecipe.domain.model.RecipeFirestore
import com.example.egyptianrecipe.domain.util.DataOrException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


@Singleton
class FireStoreRepository
@Inject
constructor(
    private val queryRecipesByName: Query
){

    suspend fun getRecipesFromFirestore(): DataOrException<List<RecipeFirestore>, Exception> {
        val dataOrException = DataOrException<List<RecipeFirestore>, Exception>()
        try {
            dataOrException.data = queryRecipesByName.get().await().map { document ->
                document.toObject(RecipeFirestore::class.java)
            }
        } catch (e: FirebaseFirestoreException) {
            dataOrException.e = e
        }
        return dataOrException
    }


    suspend fun searchFirestore(query: String): DataOrException<List<RecipeFirestore>, Exception> {
        val dataOrException = DataOrException<List<RecipeFirestore>, Exception>()
        try {

                    dataOrException.data = FirebaseFirestore.getInstance()
                        .collection("recipe")
                        .whereEqualTo("title",query)
                        .get().await().map { document ->
                            document.toObject(RecipeFirestore::class.java)
            }
        } catch (e: FirebaseFirestoreException) {
            dataOrException.e = e
        }
        return dataOrException
    }
}