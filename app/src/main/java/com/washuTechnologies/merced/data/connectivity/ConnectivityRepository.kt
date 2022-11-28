package com.washuTechnologies.merced.data.connectivity

import javax.inject.Inject

/**
 * Repository class for accessing mobile device connectivity state.
 */
class ConnectivityRepository @Inject constructor(
    connectivityDatasource: ConnectivityDatasource
) {

    /**
     * Retrieve the device connectivity state.
     */
    val connectivityState = connectivityDatasource.isInternetConnected
}