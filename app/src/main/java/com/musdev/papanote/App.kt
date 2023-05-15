package com.musdev.papanote

import android.app.Activity
import android.app.Application
import android.content.SharedPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    companion object {
        lateinit var sharedPref: SharedPreferences
    }

    private val preferencesName = "papanote-preferences"

    override fun onCreate() {
        super.onCreate()
        sharedPref = getSharedPreferences(preferencesName, Activity.MODE_PRIVATE)
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}