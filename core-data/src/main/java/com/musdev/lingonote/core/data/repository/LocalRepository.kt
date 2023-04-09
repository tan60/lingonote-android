package com.musdev.lingonote.core.data.repository

import com.musdev.lingonote.core.data.repository.database.data.DbAchieveEntity
import com.musdev.lingonote.core.data.repository.database.data.DbNoteEntity

interface LocalRepository {
    suspend fun fetchMyNotes(uid: Int): List<DbNoteEntity> = listOf()

    suspend fun postNote(note: DbNoteEntity)

    suspend fun updateNote(note: DbNoteEntity): Int

    suspend fun fetchTotalPostedCount(uid: Int) : Int

    suspend fun fetchFirstNote(uid: Int): DbNoteEntity

    suspend fun fetchAchieve(uid: Int): List<DbAchieveEntity> = listOf()
}