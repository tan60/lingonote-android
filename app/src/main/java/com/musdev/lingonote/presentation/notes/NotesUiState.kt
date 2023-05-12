package com.musdev.lingonote.presentation.notes

import com.musdev.lingonote.core.domain.entities.NoteEntity

data class NotesUiState (
    val noteItems: MutableList<NoteEntity> = mutableListOf(),
    val isFetchingNotes: Boolean = false,
    val isNoteEmpty: Boolean = true,
    var shouldUpdate: Boolean = false
)
