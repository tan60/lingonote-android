package com.musdev.papanote.core.domain.usecases

import com.musdev.papanote.core.data.repository.LocalRepository
import com.musdev.papanote.core.data.services.database.dto.Note
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend fun invoke(noteId: Int): Boolean {
        var note: Note? = localRepository.fetchNote(noteId)

        note?.let {
            return localRepository.deleteNote(note)
        } ?: return false
    }
}