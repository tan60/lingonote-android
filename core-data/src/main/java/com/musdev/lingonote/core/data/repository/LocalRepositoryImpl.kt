package com.musdev.lingonote.core.data.repository

import com.musdev.lingonote.core.data.services.DbService
import com.musdev.lingonote.core.data.services.database.dto.Achieve
import com.musdev.lingonote.core.data.services.database.dto.Note
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val dbService: DbService
) : LocalRepository {

    private val dbNoteDao by lazy { dbService.noteDBDao}

    override suspend fun fetchNotes(): List<Note> {
        return dbNoteDao.getAllNotes()
    }

    override suspend fun postNote(note: Note) {
        dbNoteDao.insertNote(note)
    }

    override suspend fun updateNote(note: Note) {
        return dbNoteDao.upsertNote(note)
    }

    override suspend fun fetchTotalPostedCount(): Int {
        return dbNoteDao.getTotalNotesCount()
    }

    override suspend fun fetchFirstNote(): Note {
        return dbNoteDao.getFirstNote()
    }

    override suspend fun fetchAchieve(): List<Achieve> {
        return dbNoteDao.getAchieves()
    }
}