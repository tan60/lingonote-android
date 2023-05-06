package com.musdev.lingonote.core.domain.di

import com.musdev.lingonote.core.data.repository.LocalRepository
import com.musdev.lingonote.core.data.repository.RemoteRepository
import com.musdev.lingonote.core.domain.usecases.AchieveUseCase
import com.musdev.lingonote.core.domain.usecases.CorrectContentUseCase
import com.musdev.lingonote.core.domain.usecases.EditUseCase
import com.musdev.lingonote.core.domain.usecases.NoteUseCase
import com.musdev.lingonote.core.domain.usecases.PreviewUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun provideAiCorrectUseCase(repository: RemoteRepository): CorrectContentUseCase {
        return CorrectContentUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideNoteUseCase(repository: LocalRepository): NoteUseCase {
        return NoteUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideEditUseCase(repository: LocalRepository): EditUseCase {
        return EditUseCase(repository)
    }
    @Singleton
    @Provides
    fun providePreviewUseCase(localRepository: LocalRepository,
                              remoteRepository: RemoteRepository): PreviewUseCase {
        return PreviewUseCase(localRepository, remoteRepository)
    }

    @Singleton
    @Provides
    fun provideAchieveUseCase(localRepository: LocalRepository): AchieveUseCase {
        return AchieveUseCase(localRepository)
    }
}