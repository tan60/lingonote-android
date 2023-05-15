package com.musdev.papanote.core.domain.di

import com.musdev.papanote.core.data.repository.LocalRepository
import com.musdev.papanote.core.data.repository.RemoteRepository
import com.musdev.papanote.core.domain.usecases.CorrectContentUseCase
import com.musdev.papanote.core.domain.usecases.DeleteNoteUseCase
import com.musdev.papanote.core.domain.usecases.FetchNotesUseCase
import com.musdev.papanote.core.domain.usecases.GetContributionUseCase
import com.musdev.papanote.core.domain.usecases.GetTotalNoteCountUseCase
import com.musdev.papanote.core.domain.usecases.PostNoteUseCase
import com.musdev.papanote.core.domain.usecases.UpdateNoteUseCase
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
    fun provideCorrectContentUseCase(repository: RemoteRepository): CorrectContentUseCase {
        return CorrectContentUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetContributionUseCase(localRepository: LocalRepository): GetContributionUseCase {
        return GetContributionUseCase(localRepository)
    }

    @Singleton
    @Provides
    fun provideGetTotalNoteCountUseCase(localRepository: LocalRepository): GetTotalNoteCountUseCase {
        return GetTotalNoteCountUseCase(localRepository)
    }

    @Singleton
    @Provides
    fun providePostNoteUseCase(localRepository: LocalRepository): PostNoteUseCase {
        return PostNoteUseCase(localRepository)
    }

    @Singleton
    @Provides
    fun provideFetchNotesUseCase(localRepository: LocalRepository): FetchNotesUseCase {
        return FetchNotesUseCase(localRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteNoteUseCase(localRepository: LocalRepository): DeleteNoteUseCase {
        return DeleteNoteUseCase(localRepository)
    }

    @Singleton
    @Provides
    fun provideUpdateNoteUseCase(localRepository: LocalRepository): UpdateNoteUseCase {
        return UpdateNoteUseCase(localRepository)
    }
}