package com.washuTechnologies.merced.ui.navigation

/**
 * Model the various screens throughout the app.
 */
sealed class Screen(val route: String) {

    /**
     * The list of rocket launches.
     */
    object LaunchList : Screen("launchList")
}
