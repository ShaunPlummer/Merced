package com.washuTechnologies.merced.util

import com.washuTechnologies.merced.data.connectivity.ConnectivityDatasource
import com.washuTechnologies.merced.data.launches.datasources.LaunchesLocalDatasource
import com.washuTechnologies.merced.data.launches.datasources.LaunchesRemoteDatasource
import com.washuTechnologies.merced.data.launches.model.RocketLaunch
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

object MockDatasourceHelper {

    fun mockLaunchesRemoteDatasource(launch: RocketLaunch) =
        mockLaunchesRemoteDatasource(launchList = arrayOf(launch))

    fun mockLaunchesRemoteDatasource(launchList: Array<RocketLaunch> = arrayOf()) =
        mockk<LaunchesRemoteDatasource> {
            coEvery { getRocketLaunchList() } returns launchList
            coEvery { getRocketLaunch(any()) } answers {
                launchList.first() { it.id == args.first() }
            }
        }

    fun mockConnectivity(isConnected: Boolean) = mockConnectivity(flow { emit(isConnected) })

    fun mockConnectivity(connectivityState: Flow<Boolean> = flow { emit(true) }) =
        mockk<ConnectivityDatasource> {
            every { isInternetConnected } returns connectivityState
        }

    fun mockLaunchesLocalSource(launch: RocketLaunch) = mockLaunchesLocalSource(arrayOf(launch))

    @Suppress("UNCHECKED_CAST")
    fun mockLaunchesLocalSource(launchList: Array<RocketLaunch> = emptyArray()) =
        mockk<LaunchesLocalDatasource> {
            val launchListFlow = MutableStateFlow(launchList)
            var rocketFlow: MutableStateFlow<RocketLaunch?>? = null

            every { getAll() } returns launchListFlow
            every { insertAll(any()) } answers {
                launchListFlow.value = args.first() as Array<RocketLaunch>
                Array(args.size) { Math.random().toLong() }
            }
            every { insert(any()) } answers {
                rocketFlow?.value = args.first() as RocketLaunch
                1
            }
            every { getLaunch(any()) } answers {
                if (rocketFlow == null) {
                    rocketFlow =
                        MutableStateFlow(launchList.firstOrNull { it.id == args.first() })
                }
                rocketFlow as Flow<RocketLaunch>
            }
            every { hasLaunch(any()) } answers {
                launchList.firstOrNull { it.id == args.first() } != null
            }
        }
}
