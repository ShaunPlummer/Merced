package com.washuTechnologies.merced.di

import com.washuTechnologies.merced.api.launches.LaunchesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun launchesApi(): LaunchesApi = LaunchesApi.create()
}
