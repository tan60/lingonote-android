package com.musdev.lingonote.core.data.services

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object ApiService {
    val TAG = "ApiService"
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
                //ignoreUnknownKeys = true
                //coerceInputValues = true
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
}