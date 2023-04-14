package com.musdev.lingonote.core.data.repository

import com.musdev.lingonote.core.data.services.database.DbService
import com.musdev.lingonote.core.data.services.database.entity.DbAchieveEntity
import com.musdev.lingonote.core.data.services.database.entity.DbNoteEntity

internal class LocalRepositoryImpl : LocalRepository {

    private val dbNoteDao by lazy { DbService.getNoteDao()}

    override suspend fun fetchMyNotes(uid: Int): List<DbNoteEntity> {
        return dbNoteDao.getAllNotes()
    }

    override suspend fun postNote(note: DbNoteEntity) {
        dbNoteDao.insert(note)
    }

    override suspend fun updateNote(note: DbNoteEntity): Int {
        return dbNoteDao.upsert(note)
    }

    override suspend fun fetchTotalPostedCount(uid: Int): Int {
        return dbNoteDao.getTotalEntityCount()
    }

    override suspend fun fetchFirstNote(uid: Int): DbNoteEntity {
        return dbNoteDao.getFirstEntity()
    }

    override suspend fun fetchAchieve(uid: Int): List<DbAchieveEntity> {
        return dbNoteDao.getAchieves()
    }
}