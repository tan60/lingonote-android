package com.musdev.lingonote.presentation.achieve

import com.musdev.lingonote.core.domain.entities.AchieveEntity

data class AchieveUiState (
    val calendarItems: List<Pair<String, AchieveEntity?>> = mutableListOf(),
    val achieveItems: MutableMap<String, AchieveEntity?> = mutableMapOf(),
    val isFetchingAchieves: Boolean = false
)