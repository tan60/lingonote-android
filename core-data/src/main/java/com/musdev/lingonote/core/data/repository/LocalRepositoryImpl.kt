package com.musdev.lingonote.core.data.repository

import com.musdev.lingonote.core.data.services.DbService
import com.musdev.lingonote.core.data.services.database.dto.Achieve
import com.musdev.lingonote.core.data.services.database.dto.Note
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val dbService: DbService,
) : LocalRepository {

    private val dbNoteDao by lazy { dbService.noteDBDao }

    override suspend fun fetchNotes(): List<Note> {
        return dbNoteDao.getAllNotes()
    }

    override suspend fun fetchNotes(limit: Int, offset: Int): List<Note> {
        return dbNoteDao.getNotes(limit, offset)
    }

    override suspend fun postNote(note: Note): Boolean {
        dbNoteDao.insertNote(note)
        return true
    }

    override suspend fun updateNote(note: Note): Boolean {
        dbNoteDao.updateNote(
            topic = note.topic,
            content = note.content,
            correctContent = note.correctedContent,
            correctedType = note.correctedType,
            fixDateTime = note.fixedDateTime,
            noteId = note.id
        )

        //dbNoteDao.upsertNote(note)
        return true
    }

    override suspend fun deleteNote(note: Note): Boolean {
        dbNoteDao.deleteNote(note)
        return true
    }

    override suspend fun fetchTotalPostedCount(): Int {
        return dbNoteDao.getTotalNotesCount()
    }

    override suspend fun fetchFirstNote(): Note {
        return dbNoteDao.getFirstNote()
    }

    override suspend fun fetchLastNote(): Note {
        return dbNoteDao.getLastNote()
    }

    override suspend fun fetchNote(noteId: Int): Note? {
        return dbNoteDao.getNote(noteId)
    }

    override suspend fun fetchAchieve(): List<Achieve> {
        return dbNoteDao.getAchieves()
    }
}