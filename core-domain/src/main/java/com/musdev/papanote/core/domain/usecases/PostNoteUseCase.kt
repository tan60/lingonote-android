package com.musdev.papanote.core.domain.usecases

import com.musdev.papanote.core.data.repository.LocalRepository
import com.musdev.papanote.core.data.services.database.dto.Note
import com.musdev.papanote.core.domain.entities.NoteEntity
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import java.util.Locale
import javax.inject.Inject

class PostNoteUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend fun invoke(noteEntity: NoteEntity): Boolean {
        val ldt = LocalDateTime()

        val formatDate = DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.KOREA)
        val formatDateTime = DateTimeFormat.forPattern("yyyy-MM-dd, HH:mm:ss").withLocale(Locale.KOREA)
        //val dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale("ko"))

        val note = Note().apply {
            this.topic = noteEntity.topic
            this.content = noteEntity.content
            this.issueDate = noteEntity.issueDate.ifEmpty { formatDate.print(ldt) }
            this.correctedType = noteEntity.correctedType
            this.fixedDateTime = noteEntity.issueDate.ifEmpty { formatDateTime.print(ldt) }
            this.issueDateTime = noteEntity.issueDate.ifEmpty { formatDateTime.print(ldt) }
            this.correctedContent = noteEntity.correctedContent
        }

        return localRepository.postNote(note)
    }
}