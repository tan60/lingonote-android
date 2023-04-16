package com.musdev.lingonote.core.data.repository

import com.musdev.lingonote.core.data.model.GPTError
import com.musdev.lingonote.core.data.model.GPTRequestModel
import com.musdev.lingonote.core.data.model.GPTResponseModel
import com.musdev.lingonote.core.data.services.ApiService
import com.musdev.lingonote.core.data.services.api.ApiResponse
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): RemoteRepository {

    val apiUrlString = "https://api.openai.com/v1/edits"
    val apiKey = "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"

    override suspend fun queryNoteContent(requestModel: GPTRequestModel): ApiResponse<Any> {
        runCatching {
            apiService.httpClient.post(apiUrlString) {
                headers {
                    //append("Content-type", "application/json; charset=UTF-8")
                    append("Authorization", "Bearer $apiKey")

                }
                contentType(ContentType.Application.Json)
                setBody(requestModel)
            }
        }.onSuccess { response ->
            return if (response.status.value in 200..299) {
                ApiResponse(response.body<GPTResponseModel>(), response.status.value)
            } else {
                ApiResponse(response.body<GPTError>(), response.status.value)
            }
            //return  it.body()
        }.onFailure {
            return ApiResponse(it.message, -1)
        }

        return ApiResponse("error", -1)
    }
}