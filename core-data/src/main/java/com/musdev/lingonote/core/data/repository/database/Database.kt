package com.musdev.lingonote.core.data.repository.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

internal abstract class NoteDatabase : RoomDatabase() {

    abstract fun insertNote()
    abstract fun updateNote()
    abstract fun queryNotes()
    abstract fun queryNoteTotalCount()
    abstract fun queryFirstNote()
    abstract fun queryAchieves()

    companion object {
        private var _instances: MutableMap<String, NoteDatabase?> = mutableMapOf()

        fun getDatabase(context: Context, userId: String): NoteDatabase {
            val db = Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "note_database").fallbackToDestructiveMigration()
                .addCallback(object: Callback() {
                    override fun onOpen(db: SupportSQLiteDatabase) {
                        db.execSQL("PRAGMA synchronous = 1")
                    }
                }
            ).build()
            _instances[userId] = db
            return _instances[userId] ?: error("DB not created")
        }

    }
}