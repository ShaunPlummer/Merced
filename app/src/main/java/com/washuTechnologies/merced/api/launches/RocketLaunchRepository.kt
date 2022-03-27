package com.washuTechnologies.merced.api.launches

import com.washuTechnologies.merced.api.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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
class RocketLaunchRepository @Inject constructor(private val launchesApi: LaunchesApi) {

    /**
     * Retrieve a list of rocket launches.
     */
    fun getLaunchList(): Flow<Result<Array<RocketLaunch>>> = flow {
        emit(Result.Loading)
        val list = launchesApi.getRocketLaunchList()
        Timber.d("List of ${list.size} rocket launches returned")
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
        val launch = launchesApi.getRocketLaunch(launchId)
        Timber.d("Retrieved flight ${launch.name}, $launchId")
        emit(Result.Success(launch))
    }.catch { exception ->
        Timber.e(exception, "Error requesting rocket launch with flight number $launchId")
        emit(Result.Error)
    }.retry(MAX_RETRY)
}
