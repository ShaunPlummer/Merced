package com.washuTechnologies.merced.util

import com.washuTechnologies.merced.api.launches.LaunchesApi
import com.washuTechnologies.merced.api.launches.RocketLaunch
import com.washuTechnologies.merced.database.launches.LaunchesLocalDatasource
import com.washuTechnologies.merced.datasources.ConnectivityDatasource
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object MockDatasourceHelper {

    fun launchesRemoteDatasource(launch: RocketLaunch) =
        launchesRemoteDatasource(launchList = arrayOf(launch))

    fun launchesRemoteDatasource(launchList: Array<RocketLaunch> = arrayOf()) = mockk<LaunchesApi> {
        coEvery { getRocketLaunchList() } returns launchList
        coEvery { getRocketLaunch(any()) } answers {
            launchList.first() { it.id == args.first() }
        }
    }

    fun mockConnectivity(isConnected: Boolean) = mockConnectivity(flow { emit(isConnected) })

    fun mockConnectivity(connectivityState: Flow<Boolean> = flow { emit(true) }) =
        mockk<ConnectivityDatasource> {
            every { hasConnectivity } returns connectivityState
        }

    fun mockLaunchesLocalSource(launchList: Array<RocketLaunch> = emptyArray()) =
        mockk<LaunchesLocalDatasource> {
            every { getAll() } returns launchList
            every { insertAll(any()) } returns emptyArray()
        }
}
