package com.musdev.papanote.di

import android.content.Context
import com.musdev.papanote.core.domain.usecases.CorrectContentUseCase
import com.musdev.papanote.core.domain.usecases.DeleteNoteUseCase
import com.musdev.papanote.core.domain.usecases.FetchNotesUseCase
import com.musdev.papanote.core.domain.usecases.GetTotalNoteCountUseCase
import com.musdev.papanote.core.domain.usecases.PostNoteUseCase
import com.musdev.papanote.core.domain.usecases.UpdateNoteUseCase
import com.musdev.papanote.presentation.edit.EditViewModel
import com.musdev.papanote.presentation.notes.NotesViewModel
import com.musdev.papanote.presentation.preview.PreviewViewModel
import com.musdev.papanote.presentation.settings.SettingViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Singleton
    @Provides
    fun provideNoteViewModel(context: Context, fetchNoteUseCase: FetchNotesUseCase, getTotalNoteCountUseCase: GetTotalNoteCountUseCase): NotesViewModel {
        return NotesViewModel(context, fetchNoteUseCase, getTotalNoteCountUseCase)
    }

    @Singleton
    @Provides
    fun provideEditViewModel(postNoteUseCase: PostNoteUseCase): EditViewModel {
        return EditViewModel(postNoteUseCase)
    }

    @Singleton
    @Provides
    fun providePreviewViewModel(correctContentUseCase: CorrectContentUseCase, deleteNoteUseCase: DeleteNoteUseCase, updateNoteUseCase: UpdateNoteUseCase): PreviewViewModel {
        return PreviewViewModel(correctContentUseCase, deleteNoteUseCase, updateNoteUseCase)
    }

    @Singleton
    @Provides
    fun provideSettingViewModel(): SettingViewModel {
        return SettingViewModel()
    }
}