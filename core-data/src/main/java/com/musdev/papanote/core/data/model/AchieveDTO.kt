package com.musdev.papanote.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AchieveDTO (
    val date: String,
    val postCount: Int
)