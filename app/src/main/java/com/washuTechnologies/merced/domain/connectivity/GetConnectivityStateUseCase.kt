package com.washuTechnologies.merced.domain.connectivity

import com.washuTechnologies.merced.data.connectivity.ConnectivityRepository
import javax.inject.Inject

/**
 * Use Case to retrieve the mobile device internet connectivity state.
 */
class GetConnectivityStateUseCase @Inject constructor(
    private val connectivityRepository: ConnectivityRepository
) {

    /**
     * Returns the mobile device connectivity state.
     */
    operator fun invoke() = connectivityRepository.connectivityState
}
