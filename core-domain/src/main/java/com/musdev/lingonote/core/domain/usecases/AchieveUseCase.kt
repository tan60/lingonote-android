package com.musdev.lingonote.core.domain.usecases

import com.musdev.lingonote.core.data.repository.LocalRepository
import com.musdev.lingonote.core.domain.entities.AchieveEntity
import com.musdev.lingonote.core.domain.entities.NoteEntity
import javax.inject.Inject

class AchieveUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend fun fetchAchieves(): MutableList<AchieveEntity> {
        val achieves = localRepository.fetchAchieve()

        var achieveEntities : MutableList<AchieveEntity> = mutableListOf()
        for (i in achieves.indices) {
            achieveEntities.add(AchieveEntity().apply {
                this.date = achieves[i].date
                this.postedCount = achieves[i].count
            })
        }

        return achieveEntities
    }

    suspend fun getFirstNote() : NoteEntity {
        val note = localRepository.fetchFirstNote()

        return NoteEntity().apply {
            this.postNo = note.id
            this.topic = note.topic
            this.content = note.content
            this.issueDate = note.issueDate
            this.correctedContent = note.correctedContent
            this.correctedType = note.correctedType
        }
    }

    suspend fun getTotalNoteCount(): Int {
        return localRepository.fetchTotalPostedCount()
    }
}