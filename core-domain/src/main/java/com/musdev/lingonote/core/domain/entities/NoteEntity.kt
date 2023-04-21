package com.musdev.lingonote.core.domain.entities

data class NoteEntity (
    var postNo: Int = -1,
    var topic: String = "",
    var content: String = "",
    var correctedContent: String = "",
    var issueDate: String = "",
    var correctedType: String = ""
)