package com.musdev.lingonote.core.data.repository

import com.musdev.lingonote.core.data.services.DbService
import com.musdev.lingonote.core.data.services.database.dto.AchieveDto
import com.musdev.lingonote.core.data.services.database.dto.NoteDto
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val dbService: DbService
) : LocalRepository {

    private val dbNoteDao by lazy { dbService.noteDBDao}

    override suspend fun fetchNotes(uid: Int): List<NoteDto> {
        return dbNoteDao.getAllNotes()
    }

    override suspend fun postNote(note: NoteDto) {
        dbNoteDao.insert(note)
    }

    override suspend fun updateNote(note: NoteDto): Int {
        return dbNoteDao.upsert(note)
    }

    override suspend fun fetchTotalPostedCount(uid: Int): Int {
        return dbNoteDao.getTotalEntityCount()
    }

    override suspend fun fetchFirstNote(uid: Int): NoteDto {
        return dbNoteDao.getFirstEntity()
    }

    override suspend fun fetchAchieve(uid: Int): List<AchieveDto> {
        return dbNoteDao.getAchieves()
    }
}