package com.musdev.papanote.core.data.services.database.dto

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "note_table")
data class Achieve (
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "count") val count: Int
)