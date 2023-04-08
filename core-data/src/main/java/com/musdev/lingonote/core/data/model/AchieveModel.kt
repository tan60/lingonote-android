package com.musdev.lingonote.core.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AchieveModel (
    @field:Json(name = "date") val date: String,
    @field:Json(name = "postCount") val postCount: Int
)