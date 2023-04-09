package com.musdev.lingonote.core.data.repository.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class DbNoteEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "post_topic") var topic: String? = null,
    @ColumnInfo(name = "post_content") var content: String? = null,
    @ColumnInfo(name = "post_improved") var improved: String? = null,
    @ColumnInfo(name = "post_issue_date") var issueDate: String? = null,
    @ColumnInfo(name = "post_issue_date_time") var issueDateTime: String? = null,
    @ColumnInfo(name = "post_fixed_date_time") var fixedDateTime: String? = null,
    @ColumnInfo(name = "post_improved_type") var improvedType: String? = null,
    @ColumnInfo(name = "post_user_uid") val userUid: Int? = null,
)

data class DbAchieveEntity (
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "count") val count: Int
)