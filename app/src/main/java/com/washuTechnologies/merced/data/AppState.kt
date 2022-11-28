package com.washuTechnologies.merced.data

/**
 * Model object representing global app state information.
 */
data class AppState(
    val isInternetConnected: Boolean
) {
    companion object {
        val DEFAULT = AppState(true)
    }
}
