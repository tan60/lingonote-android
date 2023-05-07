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
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.musdev.lingonote.presentation.TAG
import kotlinx.coroutines.Dispatchers

/**
 * viewModel은 Domain Layer 이용해 비즈니스 로직을 수행
 * UI를 그리기 위한 데이터 혹은 상태 변경에 맞게 UiState만 변경, UI를 직접 접근해서 조작하면 안됨
 */
class NotesViewModel @Inject constructor(
    private val noteUseCase: NoteUseCase
) : ViewModel() {
    var uiState by mutableStateOf(NotesUiState())
        private set

    private var fetchJob: Job? = null
    private var shouldUpdate: Boolean = false

    var notePager = Pager(PagingConfig(pageSize = NotesPagingSource.LIMIT) ) {
        NotesPagingSource(noteUseCase)
    }.flow.cachedIn(viewModelScope)

    fun fetchNotesAtFirst() {
        if (uiState.noteItems.isEmpty()) {
            fetchNotes()
        }
    }

    fun getNoteTotalCount() {
        when (fetchJob == null) {
            true -> {
                uiState = uiState.copy(isFetchingNotes = true)
                fetchJob = viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val totalCount = noteUseCase.getTotalNoteCount() //fetch data
                        uiState = uiState.copy(isNoteEmpty = totalCount == 0) //update data state
                        uiState = uiState.copy(isFetchingNotes = false) //update loading state
                        fetchJob = null
                    } catch (ioe: IOException) {
                        uiState = uiState.copy(isFetchingNotes = false) //update loading state
                        fetchJob = null
                    }
                }
            }
            false -> {
                uiState = uiState.copy(isFetchingNotes = false)
                Log.d(TAG, "getNoteTotalCount()::job is working")
            }
        }
    }

    private fun fetchNotes() {
        when (fetchJob == null) {
            true -> {
                uiState = uiState.copy(isFetchingNotes = true)
                fetchJob = viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val items = noteUseCase.fetchNotes() //fetch data
                        uiState = uiState.copy(noteItems = items) //update data state
                        uiState = uiState.copy(isFetchingNotes = false) //update loading state
                        fetchJob = null
                    } catch (ioe: IOException) {
                        uiState = uiState.copy(isFetchingNotes = false) //update loading state
                        fetchJob = null
                    }
                }
            }
            false -> {
                uiState = uiState.copy(isFetchingNotes = false)
                Log.d(TAG, "fetchNotes()::job is working")
            }
        }
    }

    fun shouldUpdate(shouldUpdate: Boolean) {
        this.shouldUpdate = shouldUpdate
        uiState.noteItems.clear()
        //fetchNotesAtFirst()
    }
}


