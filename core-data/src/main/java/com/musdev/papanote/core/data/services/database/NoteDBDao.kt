package com.musdev.papanote.core.data.services.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.musdev.papanote.core.data.services.database.dto.Achieve
import com.musdev.papanote.core.data.services.database.dto.Note

@Dao
interface NoteDBDao {
    //특정 노트 하나만 질의
    @Query("SELECT * FROM note_table WHERE id = :uid")
    suspend fun getEntity(uid: Int) : Note?

    //모든 노트 질의 - 나중에 offset, limit로 가져오게 변경 필요함
    @Query("SELECT * FROM note_table ORDER BY id DESC")
    suspend fun getAllNotes() : List<Note>

    @Query("SELECT * FROM note_table ORDER BY id DESC LIMIT :limit OFFSET :offset")
    suspend fun getNotes(limit: Int, offset: Int) : List<Note>

    @Query("SELECT MAX(id) as max FROM note_table")
    suspend fun getLastNoteIndex(): Int

    @Query("SELECT * FROM note_table WHERE id = :noteId")
    suspend fun getNote(noteId: Int): Note?

    //포스트 생성
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)


    //포스트 수정
    @Update
    suspend fun upsertNote(note: Note)

    @Query("UPDATE note_table SET topic = :topic, content = :content, corrected_content = :correctContent, fixed_date_time = :fixDateTime, corrected_type = :correctedType WHERE id = :noteId")
    suspend fun updateNote(topic: String, content: String, correctContent: String, fixDateTime: String, correctedType: String, noteId: Int)

    @Delete
    fun deleteNote(noteEntity: Note)

    //모든 노트의 갯수 질의
    @Query("SELECT COUNT(*) FROM note_table")
    suspend fun getTotalNotesCount() : Int

    //최초 노트 질의
    @Query("SELECT * FROM note_table ORDER BY id ASC LIMIT 1")
    suspend fun getFirstNote() : Note

    //마지막 작성 노트 질의
    @Query("SELECT * FROM note_table ORDER BY id DESC LIMIT 1")
    suspend fun getLastNote() : Note

    //날짜 별로 작성한 노트의 갯수 질의
    @Query("SELECT date(issue_date) AS date, COUNT(*) AS count FROM note_table GROUP BY date(issue_date)")
    suspend fun getAchieves() : List<Achieve>
}