package com.musdev.papanote.core.domain.usecases

import com.musdev.papanote.core.data.repository.LocalRepository
import javax.inject.Inject

class GetTotalNoteCountUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {

    suspend fun invoke(): Int {
        return localRepository.fetchTotalPostedCount()
    }
}