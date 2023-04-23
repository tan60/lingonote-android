package com.musdev.lingonote.di

import com.musdev.lingonote.presentation.home.HomeUiState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object HomeModule {
    /*@Singleton
    @Provides
    fun provideHomeUiState(): HomeUiState {
        return HomeUiState()
    }*/
}