package com.musdev.lingonote.presentation.preview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musdev.lingonote.App
import com.musdev.lingonote.core.domain.entities.AICorrectEntity
import com.musdev.lingonote.core.domain.entities.NoteEntity
import com.musdev.lingonote.core.domain.usecases.CorrectContentUseCase
import com.musdev.lingonote.core.domain.usecases.DeleteNoteUseCase
import com.musdev.lingonote.core.domain.usecases.UpdateNoteUseCase
import com.musdev.lingonote.shared.SharedPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class PreviewViewModel @Inject constructor(
    private val correctContentUseCase: CorrectContentUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase
): ViewModel() {

    var uiState by mutableStateOf(PreviewUiState())
        private set

    private var deleteJob: Job? = null
    private var correctJob: Job? = null
    lateinit var currentNote: NoteEntity

    fun setCurrentNote(noteEntity: NoteEntity, enableDelete: Boolean) {
        this.currentNote = noteEntity
        uiState = uiState.copy(enableDelete = enableDelete)
    }

    fun initUiState() {
        uiState = uiState.copy(deleteState = RequestState.IDLE)
        uiState = uiState.copy(correctState = RequestState.IDLE)
    }

    fun initPreviewMode() {
        setPreviewMode(PreviewMode.ORIGINAL)
    }

    fun setPreviewMode(previewMode: PreviewMode) {
        uiState = uiState.copy(previewMode = previewMode)
    }

    fun setOpenAIKey(keyValue: String) {
        with(App.sharedPref.edit()) {
            putString(SharedPref.KEY_OPENAPI, keyValue)
            apply()
        }
    }

    fun getOpenAIKey(): String {
        return App.sharedPref.getString(SharedPref.KEY_OPENAPI, "")?.let {
            it
        } ?: ""
    }

    private fun getCorrectInstruction(): String {
        return App.sharedPref.getString(SharedPref.KEY_INSTRUCTION, SharedPref.DEFAULT_VALUE_INSTRUCTION)?.let {
            it
        } ?: SharedPref.DEFAULT_VALUE_INSTRUCTION
    }

    fun correctAI(content: String) {
        if (correctJob == null) {
            uiState = uiState.copy(correctState = RequestState.REQUEST)
            try {
                correctJob = viewModelScope.launch(Dispatchers.IO) {
                    val aiCorrectEntity: AICorrectEntity = correctContentUseCase.invoke(content,
                        apiKey = getOpenAIKey(),
                        instruction = getCorrectInstruction())

                    when(aiCorrectEntity.isSuccess) {
                        true -> {
                            currentNote.correctedContent = aiCorrectEntity.correctedContent
                            updateNoteUseCase.invoke(currentNote)

                            uiState = uiState.copy(correctState = RequestState.DONE)
                            correctJob = null
                        }
                        false -> {
                            //update error message
                            aiCorrectEntity.errorMessage?.let {
                                uiState = uiState.copy(correctedError = it)
                            } ?:{
                                uiState = uiState.copy(correctedError = "unknown")
                            }

                            uiState = uiState.copy(correctState = RequestState.ERROR)
                            correctJob = null
                        }
                    }
                }

            } catch (e: Exception) {
                uiState = uiState.copy(correctState = RequestState.ERROR)
                correctJob = null
            }
        }
    }

    fun removeNote() {
        if  (deleteJob == null) {
            uiState = uiState.copy(deleteState = RequestState.REQUEST)
            deleteJob = viewModelScope.launch(Dispatchers.IO) {
                try {
                    uiState = when(deleteNoteUseCase.invoke(currentNote.postNo)) {
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