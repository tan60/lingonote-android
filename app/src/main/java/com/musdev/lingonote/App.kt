package com.musdev.lingonote

import android.app.Activity
import android.app.Application
import android.content.SharedPreferences
import com.musdev.lingonote.di.AppModule
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    private lateinit var preferences: SharedPreferences
    private val preferencesName = "lingonote-preference"

    override fun onCreate() {
        super.onCreate()
        
        preferences = getSharedPreferences(preferencesName, Activity.MODE_PRIVATE)
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}