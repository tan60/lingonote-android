package com.musdev.lingonote.core.domain.usecases

import com.musdev.lingonote.core.data.repository.LocalRepository
import com.musdev.lingonote.core.domain.entities.AchieveEntity
import javax.inject.Inject

class AchieveUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend fun getAchieve(): List<AchieveEntity> {
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
}