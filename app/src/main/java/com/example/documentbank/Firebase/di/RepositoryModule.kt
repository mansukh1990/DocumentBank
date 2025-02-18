package com.example.documentbank.Firebase.di

import com.example.documentbank.Firebase.firebaseRealtimeDb.repository.RealtimeDbRepositoryImpl
import com.example.documentbank.Firebase.firebaseRealtimeDb.repository.RealtimeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesRealtimeRepository(
        repo: RealtimeDbRepositoryImpl
    ): RealtimeRepository
}