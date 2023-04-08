package com.musdev.lingonote.core.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GPTResponseModel (
    @field:Json(name ="object") val obj: String,
    @field:Json(name = "created") val created: String,
    @field:Json(name = "choice") val choice: List<Choice>,
    @field:Json(name="usage") val usage: Usages
)


@JsonClass(generateAdapter = true)
data class Choice (
    @field:Json(name= "text") val text: String,
    @field:Json(name = "index") val index: Int
)

@JsonClass(generateAdapter = true)
data class Usages(
    @field:Json(name = "prompt_tokens") val promptTokens: Int,
    @field:Json(name = "completion_tokens") val completionTokens: Int,
    @field:Json(name ="total_tokens") val totalTokens: Int
)

