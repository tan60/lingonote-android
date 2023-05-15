package com.musdev.papanote.core.data.model

import kotlinx.serialization.Serializable

/**
 * Moshi style
 */

/*@JsonClass(generateAdapter = true)
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
)*/

@Serializable
data class GPTResponseModel(
    val `object`: String,
    val created: String,
    val choices: List<Choice>,
    val usage: Usages
)

@Serializable
data class Choice(
    val text: String,
    val index: Int
)

@Serializable
data class Usages(
    val prompt_tokens: Int,
    val completion_tokens: Int,
    val total_tokens: Int
)


@Serializable
data class GPTError(
    val error: Error
)
@Serializable
data class Error(
    val message: String,
    val type: String,
    val param: String?,
    val code: String
)

