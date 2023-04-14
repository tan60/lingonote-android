package com.musdev.lingonote.core.data.services.database

import android.content.Context

class DbService {
    companion object {
        private lateinit var context: Context
        private lateinit var noteDBDao: NoteDBDao

        fun build(context: Context) {
            Companion.context = context
            noteDBDao = RoomDB.get(context).noteItemDao()
        }

        fun getNoteDao() : NoteDBDao {
            return noteDBDao
        }
    }
}