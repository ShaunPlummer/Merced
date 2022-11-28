package com.washuTechnologies.merced.usecases

import com.washuTechnologies.merced.data.launches.RocketLaunchRepository
import com.washuTechnologies.merced.data.launches.model.RocketLaunch
import com.washuTechnologies.merced.di.IoDispatcher
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

/**
 * Use Case to retrieve a list of rocket launches.
 */
class GetRocketListUseCase @Inject constructor(
    private val rocketLaunchRepository: RocketLaunchRepository,
    @IoDispatcher val dispatcher: CoroutineDispatcher
) {

    /**
     * Returns a flow containing the launch list.
     */
    operator fun invoke(): Flow<Array<RocketLaunch>> =
        rocketLaunchRepository.getLaunchList().flowOn(dispatcher)
}