package com.musdev.lingonote.presentation.notes

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject
import androidx.compose.runtime.setValue
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.musdev.lingonote.R
import com.musdev.lingonote.core.domain.entities.NoteEntity
import com.musdev.lingonote.core.domain.usecases.FetchNotesUseCase
import com.musdev.lingonote.core.domain.usecases.GetTotalNoteCountUseCase
import com.musdev.lingonote.presentation.TAG
import kotlinx.coroutines.Dispatchers

/**
 * viewModel은 Domain Layer 이용해 비즈니스 로직을 수행
 * UI를 그리기 위한 데이터 혹은 상태 변경에 맞게 UiState만 변경, UI를 직접 접근해서 조작하면 안됨
 */
class NotesViewModel @Inject constructor(
    private val context: Context,
    private val fetchNotesUseCase: FetchNotesUseCase,
    private val getTotalNoteCountUseCase: GetTotalNoteCountUseCase
) : ViewModel() {
    var uiState by mutableStateOf(NotesUiState())
        private set

    private var fetchJob: Job? = null
    private var pagingSource: PagingSource<Int, NoteEntity>? = null

    var notePager = Pager(PagingConfig(pageSize = NotesPagingSource.LIMIT) ) {
        NotesPagingSource(this, fetchNotesUseCase).apply { pagingSource = this }
    }.flow.cachedIn(viewModelScope)


    var adLoader: AdLoader? = null

    fun initShouldUpdateState() {
        uiState = uiState.copy(shouldUpdate = false)
    }

    fun getShouldUpdateState(): Boolean {
        return uiState.shouldUpdate
    }

    fun getNoteTotalCount() {
        when (fetchJob == null) {
            true -> {
                uiState = uiState.copy(isFetchingNotes = true)
                fetchJob = viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val totalCount = getTotalNoteCountUseCase.invoke() //fetch data
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

    fun shouldUpdate(shouldUpdate: Boolean) {
        uiState = uiState.copy(shouldUpdate = shouldUpdate)
        uiState.noteItems.clear()
        pagingSource?.invalidate()
    }
}


