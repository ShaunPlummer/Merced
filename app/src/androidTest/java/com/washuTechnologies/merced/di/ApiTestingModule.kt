package com.washuTechnologies.merced.di

import com.washuTechnologies.merced.TestLaunchData
import com.washuTechnologies.merced.data.launches.datasources.LaunchesRemoteDatasource
import com.washuTechnologies.merced.data.launches.model.RocketLaunch
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ApiModule::class]
)
object ApiTestingModule {

    @Provides
    fun launchesApi(): LaunchesRemoteDatasource = FakeLaunchApi()
}


class FakeLaunchApi : LaunchesRemoteDatasource {
    override suspend fun getRocketLaunchList(): Array<RocketLaunch> = TestLaunchData.list

    override suspend fun getRocketLaunch(id: String): RocketLaunch =
        TestLaunchData.list.first { it.id == id }

}