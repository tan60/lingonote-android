package com.musdev.lingonote.core.domain.usecases

import com.musdev.lingonote.core.data.datasource.DataResponse
import com.musdev.lingonote.core.data.model.GPTRequestModel
import com.musdev.lingonote.core.data.repository.RemoteRepository
import com.musdev.lingonote.core.domain.entities.AICorrectEntity
import com.musdev.lingonote.core.domain.translator.GPTResponseModelTranslator
import com.musdev.lingonote.core.domain.translator.asDomain
import javax.inject.Inject

/**
 * 이름 규칙
 * 동사 + 명사/대상(선택사항) + UseCase
 */
class CorrectContentUseCase @Inject constructor (
    private val repository: RemoteRepository
) {
    suspend fun correctMyContent(content: String): AICorrectEntity {

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

        repository.queryGPTEdit(apiUrl, apiKey, requestModel).asDomain(GPTResponseModelTranslator).let {
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
}