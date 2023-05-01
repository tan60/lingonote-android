package com.musdev.lingonote.core.data.repository

import com.musdev.lingonote.core.data.services.database.dto.Achieve
import com.musdev.lingonote.core.data.services.database.dto.Note

interface LocalRepository {
    suspend fun fetchNotes(): List<Note> = listOf()

    suspend fun postNote(note: Note) : Boolean

    suspend fun updateNote(note: Note) : Boolean

    suspend fun fetchTotalPostedCount() : Int

    suspend fun fetchFirstNote(): Note

    suspend fun fetchAchieve(): List<Achieve> = listOf()
}