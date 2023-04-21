package com.musdev.lingonote.core.data.services.database.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class NoteDto(
    @PrimaryKey var uid: Int,
    @ColumnInfo(name = "topic") var topic: String = "",
    @ColumnInfo(name = "content") var content: String = "",
    @ColumnInfo(name = "corrected_content") var correctedContent: String = "",
    @ColumnInfo(name = "issue_date") var issueDate: String = "",
    @ColumnInfo(name = "issue_date_time") var issueDateTime: String = "",
    @ColumnInfo(name = "fixed_date_time") var fixedDateTime: String = "",
    @ColumnInfo(name = "corrected_type") var correctedType: String = "",
    @ColumnInfo(name = "user_uid") var userUid: Int = -1,
)

data class AchieveDto (
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "count") val count: Int
)