package com.washuTechnologies.merced.data.connectivity

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import timber.log.Timber

/**
 * Datasource making information about the mobile device connectivity state available to
 * other parts of the app.
 */
class ConnectivityDatasource @Inject constructor(
    connectivityManager: ConnectivityManager
) {

    private val _isInternetConnected = MutableStateFlow(false)

    /**
     * Internet connectivity state.
     */
    val isInternetConnected: Flow<Boolean>
        get() = _isInternetConnected

    init {
        connectivityManager.registerNetworkCallback(networkRequest, object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                Timber.d("Network available")
                _isInternetConnected.value = true
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                Timber.d("Network lost")
                _isInternetConnected.value = false
            }

            override fun onUnavailable() {
                Timber.d("Network unavailable")
                super.onUnavailable()
            }
        })
    }

    companion object {
        val networkRequest: NetworkRequest
            get() = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build()
    }
}