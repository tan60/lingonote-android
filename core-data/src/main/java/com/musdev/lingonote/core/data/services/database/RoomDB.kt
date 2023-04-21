package com.musdev.lingonote.core.data.services.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

import com.musdev.lingonote.core.data.services.database.dto.NoteDto

@androidx.room.Database(
    version = 1,
    entities = [NoteDto::class]
)

abstract class RoomDB : RoomDatabase() {
    abstract fun noteItemDao() : NoteDBDao

    companion object {
        private const val DB_NAME = "note-db"

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