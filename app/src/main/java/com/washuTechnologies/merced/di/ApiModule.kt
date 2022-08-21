package com.washuTechnologies.merced.di

import com.washuTechnologies.merced.data.launches.datasources.LaunchesRemoteDatasource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun launchesApi(): LaunchesRemoteDatasource = LaunchesRemoteDatasource.create()
}
