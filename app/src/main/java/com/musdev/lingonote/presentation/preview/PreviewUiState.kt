package com.musdev.lingonote.presentation.preview

import com.musdev.lingonote.core.domain.entities.NoteEntity


enum class PreviewMode {
    ORIGINAL,
    CORRECTED
}

enum class RequestState {
    IDLE,
    REQUEST,
    DONE,
    ERROR
}

data class PreviewUiState(
    val previewMode: PreviewMode = PreviewMode.ORIGINAL,
    val enableDelete: Boolean = true,
    val correctedError: String = "",
    val deleteState: RequestState = RequestState.IDLE,
    val correctState: RequestState = RequestState.IDLE,
    val updateState: RequestState = RequestState.IDLE
)