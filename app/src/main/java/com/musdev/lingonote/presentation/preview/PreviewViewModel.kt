package com.musdev.lingonote.presentation.preview

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musdev.lingonote.App
import com.musdev.lingonote.core.domain.entities.AICorrectEntity
import com.musdev.lingonote.core.domain.entities.NoteEntity
import com.musdev.lingonote.core.domain.usecases.PreviewUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class PreviewViewModel @Inject constructor(
    private val previewUseCase: PreviewUseCase
): ViewModel() {

    var uiState by mutableStateOf(PreviewUiState())
        private set

    private var deleteJob: Job? = null
    private var correctJob: Job? = null
    lateinit var currentNote: NoteEntity

    private var openAIKeyValue = ""
    private val OpenAIKeyName = "OpenAIKey"

    init {
         App.sharedPref.getString(OpenAIKeyName, "")?.let {
            openAIKeyValue = it
        }
    }

    fun setCurrentNote(noteEntity: NoteEntity, enableDelete: Boolean) {
        this.currentNote = noteEntity
        uiState = uiState.copy(correctedContent = this.currentNote.correctedContent)
        uiState = uiState.copy(enableDelete = enableDelete)
    }


    fun initUiState() {
        uiState = uiState.copy(deleteState = RequestState.IDLE)
        uiState = uiState.copy(correctState = RequestState.IDLE)
    }

    fun setPreviewMode(previewMode: PreviewMode) {
        uiState = uiState.copy(previewMode = previewMode)
    }

    fun isOpenAIKeyExist(): Boolean {
        return openAIKeyValue.isNotEmpty()
    }

    fun setOpenAIKey(keyValue: String) {
        openAIKeyValue = keyValue
        with(App.sharedPref.edit()) {
            putString(OpenAIKeyName, openAIKeyValue)
            apply()
        }
    }

    fun getOpenAIKey(): String {
        return openAIKeyValue
    }

    fun correctAI(content: String) {
        if (correctJob == null) {
            uiState = uiState.copy(correctState = RequestState.REQUEST)
            try {
                correctJob = viewModelScope.launch(Dispatchers.IO) {
                    val aiCorrectEntity: AICorrectEntity = previewUseCase.correctAI(content)

                    when(aiCorrectEntity.isSuccess) {
                        true -> {
                            currentNote.correctedContent = aiCorrectEntity.correctedContent
                            //previewUseCase.deleteNote(currentNote.postNo)
                        }
                        false -> {
                            //update error message
                        }
                    }
                    uiState = uiState.copy(deleteState = RequestState.DONE)
                    correctJob = null
                }

            } catch (e: Exception) {
                uiState = uiState.copy(deleteState = RequestState.ERROR)
                correctJob = null
            }
        }
    }

    fun removeNote() {
        if  (deleteJob == null) {
            uiState = uiState.copy(deleteState = RequestState.REQUEST)
            deleteJob = viewModelScope.launch(Dispatchers.IO) {
                try {
                    uiState = when(previewUseCase.deleteNote(currentNote.postNo)) {
                        true -> {
                            uiState.copy(deleteState = RequestState.DONE)
                        }
                        false -> {
                            uiState.copy(deleteState = RequestState.ERROR)
                        }
                    }
                    deleteJob = null
                } catch (e: Exception) {
                    uiState = uiState.copy(deleteState = RequestState.ERROR)
                    deleteJob = null
                }
            }
        }
    }

    /*fun updateCurrentNote(correctedContent: String) {
        if (updateJob == null) {
            uiState = uiState.copy(updateState = RequestState.REQUEST)
            try {
                updateJob = viewModelScope.launch(Dispatchers.IO) {
                    uiState = when(previewUseCase.updateNote(currentNote)) {
                        true -> {
                            uiState.copy(updateState = RequestState.DONE)
                        }
                        false -> {
                            uiState.copy(updateState = RequestState.ERROR)
                        }
                    }
                    updateJob = null
                }
            } catch (e: Exception) {
                uiState.copy(updateState = RequestState.ERROR)
                updateJob = null
            }
        }
    }*/
}