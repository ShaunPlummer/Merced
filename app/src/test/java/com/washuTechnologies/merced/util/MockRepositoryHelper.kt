package com.washuTechnologies.merced.util

import com.washuTechnologies.merced.data.launches.datasources.LaunchesRemoteDatasource
import com.washuTechnologies.merced.data.launches.RocketLaunchRepository
import com.washuTechnologies.merced.data.launches.datasources.LaunchesLocalDatasource
import com.washuTechnologies.merced.data.connectivity.ConnectivityDatasource

object RepositoryHelper {

    fun launchesRepository(
        launchesRemoteDatasource: LaunchesRemoteDatasource = MockDatasourceHelper.launchesRemoteDatasource(),
        localDatasource: LaunchesLocalDatasource = MockDatasourceHelper.mockLaunchesLocalSource(),
        connectivityDatasource: ConnectivityDatasource = MockDatasourceHelper.mockConnectivity()
    ) = RocketLaunchRepository(
        remoteDatasource = launchesRemoteDatasource,
        localDatasource = localDatasource,
        connectivityDatasource = connectivityDatasource
    )
}
