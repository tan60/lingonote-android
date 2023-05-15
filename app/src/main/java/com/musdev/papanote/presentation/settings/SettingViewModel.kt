package com.musdev.papanote.presentation.settings

import androidx.lifecycle.ViewModel
import com.musdev.papanote.App
import com.musdev.papanote.shared.SharedPref
import javax.inject.Inject

class SettingViewModel @Inject constructor(): ViewModel() {

    private var openAIKeyValue = ""
    private var correctInstruction = ""

    init {
        App.sharedPref.getString(SharedPref.KEY_OPENAPI, "")?.let {
            openAIKeyValue = it
        }

        App.sharedPref.getString(SharedPref.KEY_INSTRUCTION, SharedPref.DEFAULT_VALUE_INSTRUCTION)?.let {
            correctInstruction = it
        }
    }

    fun setOpenAIKey(keyValue: String) {
        openAIKeyValue = keyValue
        with(App.sharedPref.edit()) {
            putString(SharedPref.KEY_OPENAPI, openAIKeyValue)
            apply()
        }
    }

    fun getOpenAIKey(): String {
        return openAIKeyValue
    }

    fun setCorrectInstruction(instruction: String) {
        correctInstruction = instruction
        with(App.sharedPref.edit()) {
            putString(SharedPref.KEY_INSTRUCTION, correctInstruction)
            apply()
        }
    }

    fun getInstruction(): String {
        return correctInstruction
    }
}