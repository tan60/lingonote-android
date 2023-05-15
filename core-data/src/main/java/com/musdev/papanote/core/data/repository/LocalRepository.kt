package com.musdev.papanote.core.data.repository

import com.musdev.papanote.core.data.services.database.dto.Achieve
import com.musdev.papanote.core.data.services.database.dto.Note

interface LocalRepository {
    suspend fun fetchNotes(): List<Note> = listOf()

    suspend fun fetchNotes(limit: Int, offset: Int): List<Note> = listOf()

    suspend fun postNote(note: Note): Boolean

    suspend fun updateNote(note: Note): Boolean

    suspend fun deleteNote(note: Note): Boolean

    suspend fun fetchTotalPostedCount(): Int

    suspend fun fetchFirstNote(): Note

    suspend fun fetchLastNote(): Note

    suspend fun fetchNote(noteId: Int): Note?

    suspend fun fetchAchieve(): List<Achieve> = listOf()
}