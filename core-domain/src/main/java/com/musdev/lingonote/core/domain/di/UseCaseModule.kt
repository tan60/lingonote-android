package com.musdev.lingonote.core.domain.di

import com.musdev.lingonote.core.data.repository.RemoteRepository
import com.musdev.lingonote.core.domain.usecases.CorrectContentUseCase
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
}