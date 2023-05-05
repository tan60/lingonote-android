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
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext sharedPref: SharedPreferences): SharedPreferences {
        return sharedPref
    }
}