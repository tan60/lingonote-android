package com.musdev.lingonote.core.domain.translator

import com.musdev.lingonote.core.data.model.GPTErrorDTO
import com.musdev.lingonote.core.domain.entities.AICorrectEntity

object GPTErrorModelTranslator: ObjectTranslator<GPTErrorDTO, AICorrectEntity> {
    override fun asDomain(model: GPTErrorDTO): AICorrectEntity {
        return AICorrectEntity(
            isSuccess = false,
            errorMessage = model.error.message
        )
    }
}