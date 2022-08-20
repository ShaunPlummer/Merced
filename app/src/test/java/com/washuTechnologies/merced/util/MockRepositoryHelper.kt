package com.washuTechnologies.merced.util

import com.washuTechnologies.merced.api.launches.LaunchesRemoteDatasource
import com.washuTechnologies.merced.api.launches.RocketLaunchRepository
import com.washuTechnologies.merced.database.launches.LaunchesLocalDatasource
import com.washuTechnologies.merced.datasources.ConnectivityDatasource

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
