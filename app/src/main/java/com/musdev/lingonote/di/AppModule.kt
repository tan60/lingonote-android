package com.musdev.lingonote.di

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.musdev.lingonote.App
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): App {
        return app as App
    }

    @Singleton
    @Provides
    fun provideSharedPreference(sharedPreferences: SharedPreferences): SharedPreferences {
        return sharedPreferences
    }

    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        val name = "lingonote-perfence"
        preferences = context.getSharedPreferences(name, Activity.MODE_PRIVATE)

    }
}