package com.musdev.papanote.core.domain.usecases

import com.musdev.papanote.core.data.datasource.DataResponse
import com.musdev.papanote.core.data.model.GPTRequestModel
import com.musdev.papanote.core.data.repository.RemoteRepository
import com.musdev.papanote.core.domain.entities.AICorrectEntity
import com.musdev.papanote.core.domain.translator.GPTResponseModelTranslator
import com.musdev.papanote.core.domain.translator.asDomain
import javax.inject.Inject

class CorrectContentUseCase @Inject constructor (
    private val remoteRepository: RemoteRepository
) {
    suspend fun invoke(content: String, apiKey: String, instruction: String): AICorrectEntity {
        /**
         * openAI Edit
         * POST : https://api.openai.com/v1/edits
         * apiKey : Do not expose this at Github
         */

        val apiUrl = "https://api.openai.com/v1/edits"

        var requestModel = GPTRequestModel().apply {
            this.model = "text-davinci-edit-001"
            this.instruction = instruction
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
}