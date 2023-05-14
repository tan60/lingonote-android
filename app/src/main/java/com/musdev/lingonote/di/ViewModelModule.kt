package com.musdev.lingonote.di

import com.musdev.lingonote.core.domain.usecases.CorrectContentUseCase
import com.musdev.lingonote.core.domain.usecases.DeleteNoteUseCase
import com.musdev.lingonote.core.domain.usecases.FetchNotesUseCase
import com.musdev.lingonote.core.domain.usecases.GetTotalNoteCountUseCase
import com.musdev.lingonote.core.domain.usecases.PostNoteUseCase
import com.musdev.lingonote.core.domain.usecases.UpdateNoteUseCase
import com.musdev.lingonote.presentation.edit.EditViewModel
import com.musdev.lingonote.presentation.notes.NotesViewModel
import com.musdev.lingonote.presentation.preview.PreviewViewModel
import com.musdev.lingonote.presentation.settings.SettingViewModel
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
    fun provideNoteViewModel(fetchNoteUseCase: FetchNotesUseCase, getTotalNoteCountUseCase: GetTotalNoteCountUseCase): NotesViewModel {
        return NotesViewModel(fetchNoteUseCase, getTotalNoteCountUseCase)
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