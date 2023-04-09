package com.musdev.lingonote.core.data.repository.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

import com.musdev.lingonote.core.data.repository.database.data.DbNoteDao
import com.musdev.lingonote.core.data.repository.database.data.DbNoteEntity

@androidx.room.Database(
    version = 1,
    entities = [DbNoteEntity::class]
)

abstract class Database : RoomDatabase() {
    abstract fun noteItemDao() : DbNoteDao

    companion object {
        private const val DB_NAME = "note-db"

        @Volatile
        private var _instances: Database? = null

        fun getDatabase(context: Context): Database {
            synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    Database::class.java, DB_NAME).fallbackToDestructiveMigration().build()
                _instances = db
            }

            return _instances ?: error("DB not created")
        }
    }
}