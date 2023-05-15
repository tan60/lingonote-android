package com.musdev.papanote.core.data.model

import kotlinx.serialization.Serializable

/**
 * Moshi style
@JsonClass(generateAdapter = true)
data class GPTRequestModel (
    @field:Json(name = "model") val model: String,
    @field:Json(name = "input") val input: String,
    @field:Json(name = "instruction") val instruction: String
)
*/

/**
 * Kotlinx Serializtion
 */
@Serializable
data class GPTRequestModel(
    var model: String = "",
    var input: String = "",
    var instruction: String = ""
)