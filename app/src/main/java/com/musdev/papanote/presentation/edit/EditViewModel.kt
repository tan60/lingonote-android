package com.musdev.papanote.presentation.edit

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musdev.papanote.core.domain.entities.NoteEntity
import com.musdev.papanote.core.domain.usecases.PostNoteUseCase
import com.musdev.papanote.presentation.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditViewModel @Inject constructor(
    private val postNoteUseCase: PostNoteUseCase
): ViewModel() {

    var uiState by mutableStateOf(EditUiState())
        private set

    var postJob: Job? = null

    fun postNewNote(callback: (result: Boolean) -> Unit) {
        when(postJob == null) {
            true -> {
                uiState = uiState.copy(isPosting = true)
                postJob = viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val result = postNoteUseCase.invoke(buildNote(uiState.topic, uiState.content))
                        uiState.copy(isPosting = false)
                        uiState.copy(isPostComplete = true)
                        postJob = null
                        callback(result)
                    } catch (e: Exception) {
                        uiState.copy(isPosting = false)
                        uiState.copy(isPostComplete = true)
                        postJob = null
                        callback(false)
                    }
                }
            }
            false -> {
                Log.d(TAG, "postNewNote()::job is working")
                callback(false)
            }
        }
    }

    private fun buildNote(topic: String, content: String): NoteEntity  {
        return NoteEntity().apply {
            this.topic = topic
            this.content = content
        }
    }

    fun buildNoteForPreview(): NoteEntity {
        return buildNote(uiState.topic, uiState.content)
    }

    fun initUiState() {
        uiState = uiState.copy(
            topic = "",
            content = "",
            isPreviewEnable = false,
            isPostComplete = false,
            isSaveEnable = false,
            isPosting = false)
    }

    fun setTopicText(topic: String) {
        uiState = uiState.copy(topic = topic)
        updateEnable()
    }

    fun setContentText(content: String) {
        uiState = uiState.copy(content = content)
        updateEnable()
    }

    private fun updateEnable() {
        uiState = uiState.copy(isPreviewEnable = uiState.topic.isNotEmpty() && uiState.content.isNotEmpty())
        uiState = uiState.copy(isSaveEnable = uiState.topic.isNotEmpty() && uiState.content.isNotEmpty())
    }
}