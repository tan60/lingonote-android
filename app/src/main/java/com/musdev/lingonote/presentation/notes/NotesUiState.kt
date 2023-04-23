package com.musdev.lingonote.presentation.notes

import com.musdev.lingonote.core.domain.entities.NoteEntity

data class HomeUiState (
    val noteItems: MutableList<NoteEntity> = mutableListOf(),
    val isFetchingNotes: Boolean = false
)
