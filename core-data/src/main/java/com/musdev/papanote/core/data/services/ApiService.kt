package com.musdev.papanote.core.data.services

import android.util.Log
import com.musdev.papanote.core.data.datasource.DataResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object ApiService {
    const val TAG = "ApiService"
    val httpClient = HttpClient(CIO) {
        //expectSuccess = true
        install(Logging) {
            logger = object: Logger {
                override fun log(message: String) {
                    Log.d(TAG, message)
                }
            }
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
                prettyPrint = true //Json String 예쁘게 출력
                isLenient = true //따옴표 규칙 완료(REC-4627)
            })
        }
        install(DefaultRequest) {
            header("Accept", "application/json")
            header("Content-type", "application/json")
        }
        install(HttpRequestRetry) {
            retryOnServerErrors(maxRetries = 1)
        }
    }

    suspend inline fun <reified T, reified R> DataResponse<R>.post(apiUrl: String, headers: Map<String, String>, body: T): DataResponse<R> {
        runCatching {
            httpClient.post(apiUrl) {
                headers {
                    //append("Content-type", "application/json; charset=UTF-8")
                    headers.forEach {
                        append(it.key, it.value)
                    }
                }
                contentType(ContentType.Application.Json)
                setBody(body)
            }
        }.fold(
            onSuccess = { response ->
                return if (response.status.isSuccess()) {
                    return DataResponse.Success(response.body())
                } else {
                    return DataResponse.Error(response.status.value, response.status.description)
                }
            },
            onFailure = { throwable ->
                return DataResponse.Fail(throwable.message)
            }

        )
    }
}