package com.musdev.lingonote.core.data.services

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPrefService @Inject constructor(
    private val sharedPref: SharedPreferences
) {
    companion object {
        val GPT_KEY = "ChatGPT_Key"
    }

    fun getString(key: String): String {
        sharedPref.getString(key, "")?.let {
            return it
        }?: return ""
    }

    fun getInt(key: String): Int {
        return sharedPref.getInt(key, -1)
    }
}