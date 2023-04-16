package com.musdev.lingonote.core.data.di

import com.musdev.lingonote.core.data.repository.RemoteRepository
import com.musdev.lingonote.core.data.repository.RemoteRepositoryImpl
import com.musdev.lingonote.core.data.repository.Repository
import com.musdev.lingonote.core.data.services.ApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    //@Inject lateinit var remoteRepositoryImpl: RemoteRepositoryImpl

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return ApiService
    }

    @Singleton
    @Provides
    fun provideRepository(): RemoteRepository {
        return RemoteRepositoryImpl(provideApiService())
    }
}