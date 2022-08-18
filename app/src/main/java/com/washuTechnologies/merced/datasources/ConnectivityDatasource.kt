package com.washuTechnologies.merced.datasources

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/**
 * Datasource making information about the mobile device connectivity state available to
 * other parts of the app.
 */
class ConnectivityDatasource @Inject constructor(
    connectivityManager: ConnectivityManager
) {

    private val _hasConnectivity = MutableStateFlow(false)
    val hasConnectivity: Flow<Boolean>
        get() = _hasConnectivity

    init {
        connectivityManager.registerNetworkCallback(networkRequest, object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                _hasConnectivity.value = true
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                _hasConnectivity.value = false
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