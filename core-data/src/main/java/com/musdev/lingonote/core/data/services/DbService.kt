package com.musdev.lingonote.core.data.services

import android.content.Context
import com.musdev.lingonote.core.data.services.database.NoteDBDao
import com.musdev.lingonote.core.data.services.database.RoomDB
import javax.inject.Inject

class DbService @Inject constructor(private val context: Context) {
    val noteDBDao = RoomDB.get(context).noteItemDao()
}