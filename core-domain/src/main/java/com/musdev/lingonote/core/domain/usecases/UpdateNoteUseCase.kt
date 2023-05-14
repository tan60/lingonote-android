package com.musdev.lingonote.core.domain.usecases

import com.musdev.lingonote.core.data.repository.LocalRepository
import com.musdev.lingonote.core.data.services.database.dto.Note
import com.musdev.lingonote.core.domain.entities.NoteEntity
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend fun invoke(noteEntity: NoteEntity): Boolean {
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

        return localRepository.updateNote(note)
    }
}