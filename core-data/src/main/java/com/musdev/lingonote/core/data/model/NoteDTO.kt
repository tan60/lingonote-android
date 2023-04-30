package com.musdev.lingonote.core.data.model

import com.squareup.moshi.Json
import kotlinx.serialization.Serializable

@Serializable
data class NoteDTO (
    val topic: String,
    val content: String,
    val issueDate: String,
    val fixedDateTime :String,
    val aiContent: String,
    val improvedType: String,
    val uid: Int,
    val postNumber: Int
)