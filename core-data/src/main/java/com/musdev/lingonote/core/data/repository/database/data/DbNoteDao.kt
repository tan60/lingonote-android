package com.musdev.lingonote.core.data.repository.database.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface DbNoteDao {

    //모든 노트 질의 - 나중에 offset, limit로 가져오게 변경 필요함
    @Query("SELECT * FROM note_table ORDER BY uid DESC")
    fun getAllNotes() : List<DbNoteEntity>

    //특정 노트 하나만 질의
    @Query("SELECT * FROM note_table WHERE uid = :uid")
    fun getEntity(uid: Int) : DbNoteEntity

    //노트 생성
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(noteEntity: DbNoteEntity)


    //노트 수정(업데이트)
    @Transaction
    fun upsert(noteEntity: DbNoteEntity) {
        var entity = getEntity(noteEntity.uid)

        entity.run {
            topic = noteEntity.topic
            content = noteEntity.content
            improved = noteEntity.improved
            improvedType = noteEntity.improvedType
            fixedDateTime = noteEntity.fixedDateTime
        }
    }

    //모든 노트의 갯수 질의
    @Query("SELECT COUNT(*) FROM note_table")
    fun getTotalEntityCount() : Int

    //최초 노트 질의
    @Query("SELECT * FROM note_table ORDER BY uid ASC LIMIT 1")
    fun getFirstEntity() :  DbNoteEntity

    //날짜 별로 작성한 노트의 갯수 질의
    @Query("SELECT date(post_issue_date) AS date, COUNT(*) AS count FROM note_table GROUP BY date(post_issue_date)")
    fun getAchieves() : AchieveEntity
}