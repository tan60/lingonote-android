package com.musdev.papanote.presentation.notes

import com.musdev.papanote.core.domain.entities.NoteEntity

data class NotesUiState (
    val noteItems: MutableList<NoteEntity> = mutableListOf(),
    val isFetchingNotes: Boolean = false,
    val isNoteEmpty: Boolean = true,
    var shouldUpdate: Boolean = false
)
