package com.musdev.lingonote.core.domain.entities

data class AICorrectEntity (
    var isSuccess: Boolean = false,
    var correctedContent: String = "",
    var totalTokens: Int = 0,
    var errorMessage: String = ""
)