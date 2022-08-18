package com.washuTechnologies.merced.util

import com.washuTechnologies.merced.api.launches.LaunchesApi
import com.washuTechnologies.merced.api.launches.RocketLaunch
import com.washuTechnologies.merced.api.launches.RocketLaunchRepository
import com.washuTechnologies.merced.database.launches.LaunchesLocalDatasource
import com.washuTechnologies.merced.datasources.ConnectivityDatasource

object RepositoryHelper {

    fun launchesRepository(
        launchesRemoteDatasource: LaunchesApi = MockDatasourceHelper.launchesRemoteDatasource(),
        localDatasource: LaunchesLocalDatasource = MockDatasourceHelper.mockLaunchesLocalSource(),
        connectivityDatasource: ConnectivityDatasource = MockDatasourceHelper.mockConnectivity()
    ) = RocketLaunchRepository(
        launchesApi = launchesRemoteDatasource,
        localDatasource = localDatasource,
        connectivityDatasource = connectivityDatasource
    )
}
