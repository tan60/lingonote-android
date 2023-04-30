package com.musdev.lingonote.core.data.model

import com.squareup.moshi.Json
import kotlinx.serialization.Serializable

@Serializable
data class AchieveDTO (
    val date: String,
    val postCount: Int
)