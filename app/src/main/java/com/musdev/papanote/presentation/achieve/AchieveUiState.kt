package com.musdev.papanote.presentation.achieve

import com.musdev.papanote.core.domain.entities.AchieveEntity

data class AchieveUiState (
    val calendarItems: List<Pair<String, AchieveEntity?>> = mutableListOf(),
    val achieveItems: MutableMap<String, AchieveEntity?> = mutableMapOf(),
    val isFetchingAchieves: Boolean = false,
    val totalDaysCount: Int = 0,
    val totalNotesCount: Int = 0
)