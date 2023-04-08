package com.musdev.lingonote.core.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GPTRequestModel (
    @field:Json(name = "model") val model: String,
    @field:Json(name = "input") val input: String,
    @field:Json(name = "instruction") val instruction: String
)