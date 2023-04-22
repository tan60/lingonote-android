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
    suspend fun fetchNotes(): List<NoteEntity> {
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

    suspend fun postNote(noteEntity: NoteEntity) {
        val ldt = LocalDateTime()

        val formatDate = DateTimeFormat.forPattern("yyyy-MM-dd")
        val formatDateTime = DateTimeFormat.forPattern("yyyy-MM-dd, HH:mm:ss")
        //val dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale("ko"))

        val note = Note().apply {
            this.topic = noteEntity.topic
            this.content = noteEntity.content
            this.issueDate = formatDate.print(ldt)
            this.correctedType = "none"
            this.fixedDateTime = formatDateTime.print(ldt)
            this.issueDateTime = formatDateTime.print(ldt)
            this.correctedContent = ""
        }

        localRepository.postNote(note)
    }

    suspend fun updateNote(noteEntity: NoteEntity) {
        val ldt = LocalDateTime()
        val formatDateTime = DateTimeFormat.forPattern("yyyy-MM-dd, HH:mm:ss")
        //val dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale("ko"))

        val note = Note().apply {
            this.id = noteEntity.postNo
            this.topic = noteEntity.topic
            this.content = noteEntity.content
            this.correctedContent = noteEntity.correctedContent
            this.correctedType = noteEntity.correctedType
            this.fixedDateTime = formatDateTime.print(ldt)
        }

        localRepository.updateNote(note)
    }

    suspend fun getFirstNote() : NoteEntity {
        val note = localRepository.fetchFirstNote()

        return NoteEntity().apply {
            this.postNo = note.id
            this.topic = note.topic
            this.content = note.content
            this.issueDate = note.issueDate
            this.correctedContent = note.correctedContent
            this.correctedType = note.correctedType
        }
    }

    suspend fun getTotalNoteCount(): Int {
        return localRepository.fetchTotalPostedCount()
    }
}