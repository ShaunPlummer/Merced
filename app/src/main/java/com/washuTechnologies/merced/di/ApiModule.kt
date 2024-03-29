package com.washuTechnologies.merced.di

import com.washuTechnologies.merced.data.launches.datasources.LaunchesRemoteDatasource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun launchesApi(): LaunchesRemoteDatasource = LaunchesRemoteDatasource.create()
}
