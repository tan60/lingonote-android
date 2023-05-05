package com.musdev.lingonote.core.domain.usecases

import com.musdev.lingonote.core.data.datasource.DataResponse
import com.musdev.lingonote.core.data.model.GPTRequestModel
import com.musdev.lingonote.core.data.repository.LocalRepository
import com.musdev.lingonote.core.data.repository.RemoteRepository
import com.musdev.lingonote.core.data.services.database.dto.Note
import com.musdev.lingonote.core.domain.entities.AICorrectEntity
import com.musdev.lingonote.core.domain.entities.NoteEntity
import com.musdev.lingonote.core.domain.translator.GPTResponseModelTranslator
import com.musdev.lingonote.core.domain.translator.asDomain
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import javax.inject.Inject

class PreviewUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository,
) {
    suspend fun correctAI(content: String): AICorrectEntity {
        /**
         * openAI Edit
         * POST : https://api.openai.com/v1/edits
         * apiKey : Do not expose this at Github
         */

        val apiUrl = "https://api.openai.com/v1/edits"
        val apiKey = "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"//invalid key for test

        var requestModel = GPTRequestModel().apply {
            this.model = "text-davinci-edit-001"
            this.instruction = "correct and improve only grammar."
            this.input = content
        }

        remoteRepository.queryGPTEdit(apiUrl, apiKey, requestModel).asDomain(GPTResponseModelTranslator).let {
            when (it) {
                is DataResponse.Success<AICorrectEntity> -> {
                    return it.data
                }
                is DataResponse.Error->  {
                    return AICorrectEntity().apply {
                        this.isSuccess = false
                        this.errorMessage = it.errorMessage
                    }
                }
                is DataResponse.Fail -> {
                    return AICorrectEntity().apply {
                        this.isSuccess = false
                        this.errorMessage = it.failString!!
                    }
                }
            }
        }
    }

    suspend fun deleteNote(noteId: Int): Boolean {
        var note: Note? = localRepository.fetchNote(noteId)

        note?.let {
          return localRepository.deleteNote(note)
        } ?: return false
    }

    suspend fun updateNote(noteEntity: NoteEntity): Boolean {
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