package com.musdev.papanote.core.domain.usecases

import com.musdev.papanote.core.data.repository.LocalRepository
import com.musdev.papanote.core.domain.entities.NoteEntity
import javax.inject.Inject

class FetchNotesUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend fun invoke(limit: Int, offset: Int): MutableList<NoteEntity> {
        val notes = localRepository.fetchNotes(limit = limit, offset = offset)

        var noteEntities: MutableList<NoteEntity> = mutableListOf()

        for (i in notes.indices) {
            noteEntities.add(NoteEntity().apply {
                this.postNo = notes[i].id
                this.topic = notes[i].topic
                this.content = notes[i].content
                this.correctedContent = notes[i].correctedContent
                this.correctedType = notes[i].correctedType
                this.issueDate = notes[i].issueDate
            })
        }

        return noteEntities
    }
}