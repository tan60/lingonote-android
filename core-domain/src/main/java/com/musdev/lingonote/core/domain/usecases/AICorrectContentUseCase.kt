package com.musdev.lingonote.core.domain.usecases

import com.musdev.lingonote.core.data.model.GPTError
import com.musdev.lingonote.core.data.model.GPTResponseModel
import com.musdev.lingonote.core.data.repository.RemoteRepository
import com.musdev.lingonote.core.data.repository.Repository
import com.musdev.lingonote.core.domain.entities.AICorrectEntity

/**
 * 이름 규칙
 * 동사 + 명사/대상(선택사항) + UseCase
 */
class AICorrectContentUseCase(
    private val remoteRepository: RemoteRepository
) {
    suspend fun correctMyContent(content: String): AICorrectEntity {
        val result = Repository.improveNoteContent(content)

        when (result.status) {
            200 -> {
                val resultData = result.data as GPTResponseModel

                return AICorrectEntity().apply {
                    this.isSuccess = true
                    this.correctedContent = resultData.choices[0].text
                    this.totalTokens = resultData.usage.total_tokens
                }
            }
            else -> {
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