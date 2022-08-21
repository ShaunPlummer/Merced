package com.washuTechnologies.merced.data.launches

import com.washuTechnologies.merced.data.Result
import com.washuTechnologies.merced.data.connectivity.ConnectivityDatasource
import com.washuTechnologies.merced.data.launches.datasources.LaunchesLocalDatasource
import com.washuTechnologies.merced.data.launches.datasources.LaunchesRemoteDatasource
import com.washuTechnologies.merced.data.launches.model.RocketLaunch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

private const val MAX_RETRY = 3L

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
    fun getLaunchList(): Flow<Result<Array<RocketLaunch>>> = flow {
        emit(Result.Loading)

        val cachedList = localDatasource.getAll()
        emit(Result.Success(cachedList))

        if (connectivityDatasource.hasConnectivity.first()) {
            val list = queryApi().first()
            emit(list)
        }
    }.catch { exception ->
        Timber.e(exception, "Error requesting list of rocket launches")
        emit(Result.Error)
    }

    private suspend fun queryApi() = flow<Result<Array<RocketLaunch>>> {
        val list: Array<RocketLaunch> = remoteDatasource.getRocketLaunchList()
        Timber.d("${list.size} launches returned from api")
        val result = localDatasource.insertAll(list)
        Timber.d("${result.size} launches added to cache")
        emit(Result.Success(list))
    }.catch { exception ->
        Timber.e(exception, "Error requesting list of rocket launches")
        emit(Result.Error)
    }.retry(MAX_RETRY)

    /**
     * Retrieve information about a specific launch using its flight number.
     */
    fun getRocketLaunch(launchId: String): Flow<Result<RocketLaunch>> = flow {
        emit(Result.Loading)
        val launch = remoteDatasource.getRocketLaunch(launchId)
        Timber.d("Retrieved flight ${launch.name}, $launchId")
        emit(Result.Success(launch))
    }.catch { exception ->
        Timber.e(exception, "Error requesting rocket launch with flight number $launchId")
        emit(Result.Error)
    }.retry(MAX_RETRY)
}