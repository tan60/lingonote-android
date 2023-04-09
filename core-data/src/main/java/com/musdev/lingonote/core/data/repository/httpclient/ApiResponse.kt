package com.musdev.lingonote.core.data.repository.httpclient

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

data class ApiResponse<out T> (
    val data: T?,
    val status: Int
)


