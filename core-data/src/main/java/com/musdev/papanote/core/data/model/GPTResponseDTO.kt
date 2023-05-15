package com.musdev.papanote.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GPTResponseDTO(
    val `object`: String,
    val created: String,
    val choices: List<GPTChoiceDTO>,
    val usage: GPTUsagesDTO
)

@Serializable
data class GPTChoiceDTO(
    val text: String,
    val index: Int
)

@Serializable
data class GPTUsagesDTO(
    val prompt_tokens: Int,
    val completion_tokens: Int,
    val total_tokens: Int
)


@Serializable
data class GPTErrorDTO(
    val error: ErrorDTO
)

@Serializable
data class ErrorDTO(
    val message: String?,
    val type: String?,
    val param: String?,
    val code: String?
)