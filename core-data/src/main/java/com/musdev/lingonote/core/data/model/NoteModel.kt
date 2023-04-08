package com.musdev.lingonote.core.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NoteModel (
    @field:Json(name = "topic") val topic: String,
    @field:Json(name = "content") val content: String,
    @field:Json(name = "issueDate") val issueDate: String,
    @field:Json(name = "fixedDateTime") val fixedDateTime :String,
    @field:Json(name = "aiContent") val aiContent: String,
    @field:Json(name = "improvedType") val improvedType: String,
    @field:Json(name = "uid") val uid: Int,
    @field:Json(name = "postNumber") val postNumber: Int
)