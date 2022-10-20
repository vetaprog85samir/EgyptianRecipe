package com.example.egyptianrecipe.di

import android.content.Context
import androidx.compose.runtime.key
import com.example.egyptianrecipe.presentation.BaseApplication
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Query.*
import com.google.firebase.firestore.Query.Direction.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.tasks.await
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent ::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideQueryRecipesByName() = FirebaseFirestore.getInstance()
        .collection("recipe")
        .orderBy("title")


}