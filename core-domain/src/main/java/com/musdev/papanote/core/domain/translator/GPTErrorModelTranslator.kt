package com.musdev.papanote.core.domain.translator

import com.musdev.papanote.core.data.model.GPTErrorDTO
import com.musdev.papanote.core.domain.entities.AICorrectEntity

object GPTErrorModelTranslator: ObjectTranslator<GPTErrorDTO, AICorrectEntity> {
    override fun asDomain(model: GPTErrorDTO): AICorrectEntity {
        return AICorrectEntity(
            isSuccess = false,
            errorMessage = model.error.message
        )
    }
}