package com.musdev.lingonote.core.data.repository

import com.musdev.lingonote.core.data.services.database.dto.AchieveDto
import com.musdev.lingonote.core.data.services.database.dto.NoteDto

interface LocalRepository {
    suspend fun fetchNotes(uid: Int): List<NoteDto> = listOf()

    suspend fun postNote(note: NoteDto)

    suspend fun updateNote(note: NoteDto): Int

    suspend fun fetchTotalPostedCount(uid: Int) : Int

    suspend fun fetchFirstNote(uid: Int): NoteDto

    suspend fun fetchAchieve(uid: Int): List<AchieveDto> = listOf()
}