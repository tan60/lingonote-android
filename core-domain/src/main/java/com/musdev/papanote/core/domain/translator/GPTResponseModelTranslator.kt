package com.musdev.papanote.core.domain.translator

import com.musdev.papanote.core.data.model.GPTResponseDTO
import com.musdev.papanote.core.domain.entities.AICorrectEntity

object GPTResponseModelTranslator: ObjectTranslator<GPTResponseDTO, AICorrectEntity> {
    override fun asDomain(model: GPTResponseDTO): AICorrectEntity {

        return AICorrectEntity(
            isSuccess = true,
            correctedContent = model.choices[0].text,
            totalTokens = model.usage.total_tokens
        )
    }
}