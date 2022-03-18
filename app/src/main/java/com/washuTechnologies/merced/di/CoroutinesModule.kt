package com.washuTechnologies.merced.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {

    /**
     * Allows [Dispatchers.Default] to be injected using dagger by annotating a [CoroutineDispatcher]
     * with the [DefaultDispatcher] annotation.
     */
    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    /**
     * Allows [Dispatchers.IO] to be injected using dagger by annotating a [CoroutineDispatcher]
     * with the [IoDispatcher] annotation.
     */
    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    /**
     * Allows [Dispatchers.Main] to be injected using dagger by annotating a [CoroutineDispatcher]
     * with the [MainDispatcher] annotation.
     */
    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}
