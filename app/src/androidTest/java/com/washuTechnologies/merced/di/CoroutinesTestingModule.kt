package com.washuTechnologies.merced.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CoroutineModule::class]
)
object CoroutinesTestingModule {

    /**
     * Allows [Dispatchers.Default] to be injected using dagger by annotating a [CoroutineDispatcher]
     * with the [DefaultDispatcher] annotation.
     */
    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = UnconfinedTestDispatcher()

    /**
     * Allows [Dispatchers.IO] to be injected using dagger by annotating a [CoroutineDispatcher]
     * with the [IoDispatcher] annotation.
     */
    @IoDispatcher
    @Provides
    @Singleton
    fun providesIoDispatcher(): CoroutineDispatcher = UnconfinedTestDispatcher()

    /**
     * Allows [Dispatchers.Main] to be injected using dagger by annotating a [CoroutineDispatcher]
     * with the [MainDispatcher] annotation.
     */
    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = UnconfinedTestDispatcher()
}
