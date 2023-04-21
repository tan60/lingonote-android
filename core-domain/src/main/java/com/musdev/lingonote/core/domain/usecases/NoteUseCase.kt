package com.musdev.lingonote.core.domain.usecases

import com.musdev.lingonote.core.data.repository.LocalRepository
import com.musdev.lingonote.core.data.services.database.dto.NoteDto
import com.musdev.lingonote.core.domain.entities.NoteEntity
import org.joda.time.DateTime
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class NoteUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend fun fetchNotes(): List<NoteEntity> {
        val noteDtos = localRepository.fetchNotes(123)

        var noteEntities: MutableList<NoteEntity> = mutableListOf()

        for (i in noteDtos.indices) {
            noteEntities.add(NoteEntity().apply {
                this.topic = noteDtos[i].topic
                this.content = noteDtos[i].content
                this.correctedContent = noteDtos[i].correctedContent
                this.correctedType = noteDtos[i].correctedType
                this.issueDate = noteDtos[i].issueDate
                this.postNo = noteDtos[i].uid
            })
        }

        return noteEntities
    }

    suspend fun postNote(noteEntity: NoteEntity): Int {

        val ldt = LocalDateTime()

        val formatDate: DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd")
        val formatDateTime: DateTimeFormatter = DateTimeFormat.forPattern("yyyy, MMMM dd, HH:mm:ss")
        val str: String = fmt.print(ldt)



        //val dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale("ko"))

        NoteDto().apply {
            this.uid = 1
            this.topic = noteEntity.topic
            this.content = noteEntity.content
            this.issueDate = formatDate.print(ldt)
            this.correctedType = "none"
            this.fixedDateTime = formatDateTime.print(ldt)
            this.issueDateTime = formatDateTime.print(ldt)
            this.correctedContent = ""
            this.userUid = 123
        }

    }
}