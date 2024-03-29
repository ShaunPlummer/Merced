package com.washuTechnologies.merced.usecases

import com.washuTechnologies.merced.data.launches.RocketLaunchRepository
import com.washuTechnologies.merced.data.launches.model.RocketLaunch
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

/**
 * Use Case to retrieve a list of rocket launches.
 */
class GetRocketListUseCase @Inject constructor(
    private val rocketLaunchRepository: RocketLaunchRepository
) {

    /**
     * Returns a flow containing the launch list.
     */
    operator fun invoke(): Flow<Array<RocketLaunch>> = rocketLaunchRepository.getLaunchList()
}