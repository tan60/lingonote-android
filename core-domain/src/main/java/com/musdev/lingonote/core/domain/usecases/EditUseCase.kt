package com.musdev.lingonote.core.domain.usecases

import com.musdev.lingonote.core.data.repository.LocalRepository
import com.musdev.lingonote.core.data.services.database.dto.Note
import com.musdev.lingonote.core.domain.entities.NoteEntity
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import javax.inject.Inject

class EditUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend fun postNote(noteEntity: NoteEntity): Boolean {
        val ldt = LocalDateTime()

        val formatDate = DateTimeFormat.forPattern("yyyy-MM-dd")
        val formatDateTime = DateTimeFormat.forPattern("yyyy-MM-dd, HH:mm:ss")
        //val dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale("ko"))

        val note = Note().apply {
            this.topic = noteEntity.topic
            this.content = noteEntity.content
            this.issueDate = formatDate.print(ldt)
            this.correctedType = noteEntity.correctedType
            this.fixedDateTime = formatDateTime.print(ldt)
            this.issueDateTime = formatDateTime.print(ldt)
            this.correctedContent = noteEntity.correctedContent
        }

        return localRepository.postNote(note)
    }
}