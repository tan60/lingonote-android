package com.musdev.lingonote.core.data.services.api

data class ApiResponse<out T> (
    val data: T?,
    val status: Int
)


