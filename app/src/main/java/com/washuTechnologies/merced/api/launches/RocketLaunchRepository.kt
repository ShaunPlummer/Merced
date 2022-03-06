package com.washuTechnologies.merced.api.launches

import com.washuTechnologies.merced.api.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
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
    fun getLaunchList(): Flow<Result<List<RocketLaunch>>> = flow {
        emit(Result.Loading())
        val list = launchesApi.getRocketLaunchList()
        emit(Result.Success(list))
    }.catch {
        emit(Result.Error())
    }.retry(MAX_RETRY)
}
