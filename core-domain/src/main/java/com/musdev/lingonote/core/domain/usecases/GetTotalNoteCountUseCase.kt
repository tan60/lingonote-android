package com.musdev.lingonote.core.domain.usecases

import com.musdev.lingonote.core.data.repository.LocalRepository
import javax.inject.Inject

class GetTotalNoteCountUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {

    suspend fun invoke(): Int {
        return localRepository.fetchTotalPostedCount()
    }
}