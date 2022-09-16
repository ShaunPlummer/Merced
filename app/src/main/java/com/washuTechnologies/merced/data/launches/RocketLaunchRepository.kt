package com.washuTechnologies.merced.data.launches

import com.washuTechnologies.merced.data.connectivity.ConnectivityDatasource
import com.washuTechnologies.merced.data.launches.datasources.LaunchesLocalDatasource
import com.washuTechnologies.merced.data.launches.datasources.LaunchesRemoteDatasource
import com.washuTechnologies.merced.data.launches.model.RocketLaunch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository class for accessing the list of rocket launches.
 */
@Singleton
class RocketLaunchRepository @Inject constructor(
    private val remoteDatasource: LaunchesRemoteDatasource,
    private val localDatasource: LaunchesLocalDatasource,
    private val connectivityDatasource: ConnectivityDatasource
) {

    /**
     * Retrieve a list of rocket launches.
     */
    fun getLaunchList(): Flow<Array<RocketLaunch>> = localDatasource.getAll().onStart {
        checkForLaunchListUpdate()
    }.catch { exception ->
        Timber.e(exception, "Error requesting list of rocket launches")
        emit(emptyArray())
    }

    private suspend fun checkForLaunchListUpdate() {
        if (!connectivityDatasource.isInternetConnected.first()) {
            Timber.e("Unable to sync launch list, not connected")
            return
        }
        try {
            val list: Array<RocketLaunch> = remoteDatasource.getRocketLaunchList()
            Timber.d("${list.size} launches returned from api")
            val result = localDatasource.insertAll(list)
            Timber.d("${result.size} launches added to cache")
        } catch (e: Exception) {
            Timber.e(e, "Error when querying API for launch list")
        }
    }

    /**
     * Retrieve information about a specific launch using its flight number.
     */
    fun getRocketLaunch(launchId: String): Flow<RocketLaunch?> =
        localDatasource.getLaunch(launchId).onStart {
            getRemoteLaunch(launchId)
        }.catch { exception ->
            Timber.e(exception, "Error requesting list of rocket launches")
            emit(null)
        }

    private suspend fun getRemoteLaunch(launchId: String) {
        if (!connectivityDatasource.isInternetConnected.first()) {
            Timber.e("Unable to sync launch list, not connected")
            return
        }
        try {
            val launch = remoteDatasource.getRocketLaunch(launchId)
            Timber.d("Info for $launchId returned from api")
            localDatasource.insert(launch)
        } catch (e: Exception) {
            Timber.e(e, "Error when querying API for launch $launchId")
        }
    }
}
