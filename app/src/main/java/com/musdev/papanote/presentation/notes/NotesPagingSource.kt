package com.musdev.papanote.presentation.notes

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.musdev.papanote.core.domain.entities.NoteEntity
import com.musdev.papanote.core.domain.usecases.FetchNotesUseCase
import com.musdev.papanote.presentation.TAG
import java.io.IOException

class NotesPagingSource (
    private val viewModel: NotesViewModel,
    private val fetchNotesUseCase: FetchNotesUseCase
): PagingSource<Int, NoteEntity>() {
    companion object {
        private const val STARTING_PAGING_INDEX = 0
        const val LIMIT = 10
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NoteEntity> {
        try {
            val position = if (viewModel.getShouldUpdateState()) STARTING_PAGING_INDEX else params.key?: STARTING_PAGING_INDEX
            val notes = fetchNotesUseCase.invoke(limit = LIMIT, offset = position)

            viewModel.initShouldUpdateState()
            Log.d(TAG, "load offset = $position ")
            return if (notes != emptyList<NoteEntity>()) {
                LoadResult.Page(
                    data = notes,
                    prevKey = when(position) {
                        STARTING_PAGING_INDEX -> null
                        else -> position - LIMIT
                    },
                    nextKey = when(notes.size < LIMIT) {
                        true -> null
                        false -> position + LIMIT
                    }
                )
            } else {
                LoadResult.Invalid()
            }
        } catch (e: IOException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NoteEntity>): Int? {
        return state.anchorPosition
    }
}