package com.washuTechnologies.merced.util

import com.washuTechnologies.merced.data.launches.datasources.LaunchesRemoteDatasource
import com.washuTechnologies.merced.data.launches.RocketLaunchRepository
import com.washuTechnologies.merced.data.launches.datasources.LaunchesLocalDatasource
import com.washuTechnologies.merced.data.connectivity.ConnectivityDatasource

object RepositoryHelper {

    fun launchesRepository(
        remoteDatasource: LaunchesRemoteDatasource = MockDatasourceHelper.mockLaunchesRemoteDatasource(),
        localDatasource: LaunchesLocalDatasource = MockDatasourceHelper.mockLaunchesLocalSource(),
        connectivityDatasource: ConnectivityDatasource = MockDatasourceHelper.mockConnectivity()
    ) = RocketLaunchRepository(
        remoteDatasource = remoteDatasource,
        localDatasource = localDatasource,
        connectivityDatasource = connectivityDatasource
    )
}
