package com.musdev.lingonote.core.domain.usecases

import android.content.SharedPreferences
import com.musdev.lingonote.core.data.repository.LocalRepository
import com.musdev.lingonote.core.data.services.database.dto.Note
import com.musdev.lingonote.core.domain.entities.NoteEntity
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import javax.inject.Inject

class NoteUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend fun fetchNotes(): MutableList<NoteEntity> {
        val notes = localRepository.fetchNotes()

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

    suspend fun fetchLastNote() : NoteEntity {
        val note = localRepository.fetchLastNote()

        return NoteEntity().apply {
            this.postNo = note.id
            this.topic = note.topic
            this.content = note.content
            this.issueDate = note.issueDate
            this.correctedContent = note.correctedContent
            this.correctedType = note.correctedType
        }
    }
}