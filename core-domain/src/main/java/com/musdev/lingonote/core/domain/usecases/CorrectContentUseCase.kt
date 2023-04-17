package com.musdev.lingonote.core.domain.usecases

import com.musdev.lingonote.core.data.model.GPTError
import com.musdev.lingonote.core.data.model.GPTRequestModel
import com.musdev.lingonote.core.data.model.GPTResponseModel
import com.musdev.lingonote.core.data.repository.RemoteRepository
import com.musdev.lingonote.core.domain.entities.AICorrectEntity
import javax.inject.Inject

/**
 * 이름 규칙
 * 동사 + 명사/대상(선택사항) + UseCase
 */
class CorrectContentUseCase @Inject constructor (
    private val repository: RemoteRepository
) {
    suspend fun correctMyContent(content: String): AICorrectEntity {

        var requestModel = GPTRequestModel().apply {
            this.model = "text-davinci-edit-001"
            this.input = "Hi, this is DoHyoung Kim and I am Android developer. Am I doing now?"
            this.instruction = "correct and improve only grammar."
        }

        val result = repository.queryNoteContent(requestModel)

        when (result.status) {
            200 -> {
                val resultData = result.data as GPTResponseModel

                //convert response model to Entity
                return AICorrectEntity().apply {
                    this.isSuccess = true
                    this.correctedContent = resultData.choices[0].text
                    this.totalTokens = resultData.usage.total_tokens
                }
            }
            else -> {
                //convert response model to Entity
                return AICorrectEntity().apply {
                    this.isSuccess = false
                    this.errorMessage = if (result.data is GPTError) {
                        (result.data as GPTError).error.message
                    } else {
                        result.data as String
                    }
                }
            }
        }
    }
}