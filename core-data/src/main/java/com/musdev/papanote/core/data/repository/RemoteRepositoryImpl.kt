package com.musdev.papanote.core.data.repository

import com.musdev.papanote.core.data.datasource.DataResponse
import com.musdev.papanote.core.data.model.GPTErrorDTO
import com.musdev.papanote.core.data.model.GPTRequestModel
import com.musdev.papanote.core.data.model.GPTResponseDTO
import com.musdev.papanote.core.data.services.ApiService
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): RemoteRepository {

    override suspend fun queryGPTEdit(apiUrl: String, apiKey: String, requestBody: GPTRequestModel): DataResponse<GPTResponseDTO> {
        runCatching {
            apiService.httpClient.post(apiUrl) {
                headers {
                    append("Authorization", "Bearer $apiKey")
                }
                contentType(ContentType.Application.Json)
                setBody(requestBody)
            }
        }.fold(
            onSuccess = { response ->
                return if (response.status.isSuccess()) {
                    return DataResponse.Success(response.body())
                } else {
                    when(response.status.value) {
                        in (400..499) -> {
                            /**
                             * openAI error document
                             * https://platform.openai.com/docs/guides/error-codes/api-errors
                             */
                            val gptErrorDto: GPTErrorDTO = response.body()
                            return DataResponse.Error(response.status.value, gptErrorDto.error.message)
                        }
                        else -> {
                            return DataResponse.Error(response.status.value, response.status.description)
                        }
                    }
                }
            },
            onFailure = { throwable ->
                return DataResponse.Fail(throwable.message)
            }
        )
    }
}