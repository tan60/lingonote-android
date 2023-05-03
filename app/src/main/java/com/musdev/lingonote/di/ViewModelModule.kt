package com.musdev.lingonote.di

import com.musdev.lingonote.core.domain.usecases.EditUseCase
import com.musdev.lingonote.core.domain.usecases.NoteUseCase
import com.musdev.lingonote.presentation.edit.EditViewModel
import com.musdev.lingonote.presentation.notes.NotesViewModel
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
    fun provideNoteViewModel(noteUseCase: NoteUseCase): NotesViewModel {
        return NotesViewModel(noteUseCase = noteUseCase)
    }

    @Singleton
    @Provides
    fun provideEditViewModel(editUseCase: EditUseCase): EditViewModel {
        return EditViewModel(editUseCase)
    }
}