package com.musdev.lingonote.presentation.notes

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musdev.lingonote.core.domain.usecases.NoteUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject
import androidx.compose.runtime.setValue
import com.musdev.lingonote.presentation.home.TAG

/**
 * viewModel은 Domain Layer 이용해 비즈니스 로직을 수행
 * UI를 그리기 위한 데이터 혹은 상태 변경 값에 따라 UiState를 변경해 준다.
 * UI를 직접 접근해서 조작하면 안됨
 */
class NotesViewModel @Inject constructor(
    private val noteUseCase: NoteUseCase
) : ViewModel() {
    var uiState by mutableStateOf(HomeUiState())
        private set

    private var fetchJob: Job? = null

    fun fetchNotesAtFirst() {
        Log.d(TAG, "fetchNotesAtFirst()")
        when (uiState.noteItems.isEmpty()) {
            true -> {
                fetchNotes()
            }
            else -> {
                //items are exist
            }
        }
    }
    private fun fetchNotes() {
        Log.d(TAG, "fetchNotes()")
        when (fetchJob == null) {
            true -> {
                uiState = uiState.copy(isFetchingNotes = true)
                fetchJob = viewModelScope.launch {
                    try {
                        val items = noteUseCase.fetchNotes() //fetch data
                        uiState = uiState.copy(noteItems = items) //update data state
                        uiState = uiState.copy(isFetchingNotes = false) //update loading state

                        Log.d(TAG, "fetchNotes()::get response")
                    } catch (ioe: IOException) {
                        uiState = uiState.copy(isFetchingNotes = false) //update loading state
                    }
                }
            }

            false -> {
                Log.d(TAG, "fetchNotes()::job is working")
            }
        }
    }
}


