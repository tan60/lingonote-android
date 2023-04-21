package com.musdev.lingonote.core.data.services.preference

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

object PrefService {
    const val USER_UID = "user_uid"
    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        val name = "lingonote-perfence"
        preferences = context.getSharedPreferences(name, Activity.MODE_PRIVATE)

    }
}