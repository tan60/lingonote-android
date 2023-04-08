package com.musdev.lingonote.core.data.repository.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

abstract class Database : RoomDatabase() {

    abstract fun insertNote()
    abstract fun updateNote()
    abstract fun queryNotes()
    abstract fun queryNoteTotalCount()
    abstract fun queryFirstNote()
    abstract fun queryAchieves()

    companion object {
        private const val DB_NAME = "note-db"
        private var _instances: Database? = null

        fun getDatabase(context: Context): Database {
            synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    Database::class.java, DB_NAME).build()
                _instances = db
            }

            return _instances ?: error("DB not created")
        }
    }
}