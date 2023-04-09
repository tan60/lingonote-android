package com.musdev.lingonote.core.data.repository

import com.musdev.lingonote.core.data.model.GPTRequestModel
import com.musdev.lingonote.core.data.model.GPTResponseModel
import com.musdev.lingonote.core.data.repository.httpclient.KtorClient
import com.musdev.lingonote.core.data.repository.httpclient.ApiResponse
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

internal class RemoteRepositoryImpl : RemoteRepository {

    val apiUrlString = "https://api.openai.com/v1/edits"
    val apiKey = "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"

    override suspend fun queryNoteContent(requestModel: GPTRequestModel): ApiResponse<Any> {
        runCatching {
            KtorClient.client.post(apiUrlString) {
                headers {
                    //append("Content-type", "application/json; charset=UTF-8")
                    append("Authorization", "Bearer $apiKey")

                }
                contentType(ContentType.Application.Json)
                setBody(requestModel)
            }
        }.onSuccess {
            return ApiResponse(it.body<GPTResponseModel>(), it.status.value)
            //return  it.body()
        }.onFailure {
            return ApiResponse(it.message, -1)
        }

        return ApiResponse("error", -1)
    }
}