package com.musdev.papanote.core.data.services.database.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    @ColumnInfo(name = "topic") var topic: String = "",
    @ColumnInfo(name = "content") var content: String = "",
    @ColumnInfo(name = "corrected_content") var correctedContent: String = "",
    @ColumnInfo(name = "issue_date") var issueDate: String = "",
    @ColumnInfo(name = "issue_date_time") var issueDateTime: String = "",
    @ColumnInfo(name = "fixed_date_time") var fixedDateTime: String = "",
    @ColumnInfo(name = "corrected_type") var correctedType: String = ""
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}