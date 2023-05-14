package com.musdev.lingonote.presentation.achieve

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musdev.lingonote.core.domain.entities.AchieveEntity
import com.musdev.lingonote.core.domain.usecases.GetContributionUseCase
import com.musdev.lingonote.core.domain.usecases.GetTotalNoteCountUseCase
import com.musdev.lingonote.presentation.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AchieveViewModel @Inject constructor(
    private val getContributionUseCase: GetContributionUseCase,
    private val getTotalNoteCountUseCase: GetTotalNoteCountUseCase
): ViewModel() {
    var uiState by mutableStateOf(AchieveUiState())
        private set

    private var fetchJob: Job? = null
    private var shouldUpdate: Boolean = false

    fun fetchAchieveAtFirst() {
        if (uiState.achieveItems.isEmpty()) {
            fetchAchieves()
        }
    }

    private fun fetchAchieves() {
        when (fetchJob == null) {
            true -> {
                uiState = uiState.copy(isFetchingAchieves = true)
                fetchJob = viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val items = getContributionUseCase.invoke() //fetch data
                        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)

                        if (items.isNotEmpty()) {
                            //노트 처음 작성한 날짜와 현재 날짜 비교로 일수 구하기
                            val inputDate = inputDateFormat.parse(items[0].date)
                            val currentDate = Date()

                            val diffInMillis = currentDate.time - inputDate.time
                            val diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis)

                            uiState = uiState.copy(totalDaysCount = diffInDays.toInt() + 1)
                            //uiState = uiState.copy(totalNotesCount = items.size)

                            var calendarItems: MutableMap<String, AchieveEntity?> = mutableMapOf()
                            val calendar = Calendar.getInstance()
                            calendar.time = inputDate

                            for (i in 0.. diffInDays.toInt()) {
                                val date = inputDateFormat.format(calendar.time)
                                calendarItems[date] = getMatchDateItem(date, items)
                                calendar.add(Calendar.DAY_OF_YEAR, 1)
                            }

                            uiState = uiState.copy(achieveItems = calendarItems)
                            uiState = uiState.copy(calendarItems = calendarItems.toList())
                            uiState = uiState.copy(isFetchingAchieves = false) //update loading state
                            fetchJob = null
                        }

                        val totalCount = getTotalNoteCountUseCase.invoke()
                        uiState = uiState.copy(totalNotesCount = totalCount)

                    } catch (ioe: IOException) {
                        uiState = uiState.copy(isFetchingAchieves = false) //update loading state
                        fetchJob = null
                    }
                }
            }
            false -> {
                uiState = uiState.copy(isFetchingAchieves = false)
                Log.d(TAG, "fetchAchieves()::job is working")
            }
        }
    }

    fun shouldUpdate(shouldUpdate: Boolean) {
        this.shouldUpdate = shouldUpdate
        uiState.achieveItems.clear()
    }

    private fun getMatchDateItem(date: String, items: MutableList<AchieveEntity>): AchieveEntity? {
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        val outputDateFormat = SimpleDateFormat("M/d", Locale.KOREA)

        for (i in 0 until items.size) {
            if (date == items[i].date) {
                val date = inputDateFormat.parse(items[i].date)
                val outputDateString = outputDateFormat.format(date)
                items[i].date = outputDateString

                return items[i]
            }
        }
        return null
    }
}