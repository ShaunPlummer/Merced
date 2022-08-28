package com.washuTechnologies.merced.usecases

import com.washuTechnologies.merced.data.launches.RocketLaunchRepository
import com.washuTechnologies.merced.data.launches.model.RocketLaunch
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRocketListUseCase @Inject constructor(
    private val rocketLaunchRepository: RocketLaunchRepository
) {

    operator fun invoke(): Flow<Array<RocketLaunch>> = rocketLaunchRepository.getLaunchList()
}
