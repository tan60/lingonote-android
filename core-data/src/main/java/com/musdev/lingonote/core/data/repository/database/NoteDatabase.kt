package com.musdev.lingonote.core.data.repository.database

import android.content.Context
import com.musdev.lingonote.core.data.repository.database.data.DbNoteDao

class NoteDatabase {
    companion object {
        private lateinit var context: Context
        private lateinit var dbNoteDao: DbNoteDao

        fun build(context: Context) {
            this.context = context
            dbNoteDao = Database.getDatabase(context).noteItemDao()
        }

        fun getNoteDao() : DbNoteDao {
            return dbNoteDao
        }
    }
}