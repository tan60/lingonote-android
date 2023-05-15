package com.musdev.papanote.core.data.services.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

import com.musdev.papanote.core.data.services.database.dto.Note

@androidx.room.Database(
    version = 1,
    entities = [Note::class]
)

abstract class RoomDB : RoomDatabase() {
    abstract fun noteItemDao() : NoteDBDao

    companion object {
        private const val DB_NAME = "lingonote-db"

        @Volatile
        private var _instances: RoomDB? = null

        fun get(context: Context): RoomDB {
            synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDB::class.java, DB_NAME
                ).fallbackToDestructiveMigration().build()
                _instances = db
            }

            return _instances ?: error("DB not created")
        }
    }
}