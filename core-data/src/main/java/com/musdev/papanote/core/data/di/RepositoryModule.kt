package com.musdev.papanote.core.data.di

import android.content.Context
import com.musdev.papanote.core.data.repository.LocalRepository
import com.musdev.papanote.core.data.repository.LocalRepositoryImpl
import com.musdev.papanote.core.data.repository.RemoteRepository
import com.musdev.papanote.core.data.repository.RemoteRepositoryImpl
import com.musdev.papanote.core.data.services.ApiService
import com.musdev.papanote.core.data.services.DbService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return ApiService
    }

    @Singleton
    @Provides
    fun provideDBService(context: Context): DbService {
        return DbService(context)
    }

    @Singleton
    @Provides
    fun provideRemoteRepository(apiService: ApiService): RemoteRepository {
        return RemoteRepositoryImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideLocalRepository(dbService: DbService): LocalRepository {
        return LocalRepositoryImpl(dbService)
    }
}