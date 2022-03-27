package com.washuTechnologies.merced.ui.navigation

/**
 * Model the various screens throughout the app.
 */
sealed class Screen(val route: String) {
    /**
     * A screen displaying a list of rocket launches.
     */
    object LaunchList : Screen("launchList")

    /**
     * A screen displaying detailed information about a rocket launch.
     */
    object LaunchDetail : Screen("launchDetail/{id}") {
        /**
         * Navigation argument for the flight number.
         */
        const val launchIdKey = "id"

        /**
         * Create the route string for use with the compose navigation.
         */
        fun create(launchId: String) = "launchDetail/$launchId"
    }
}
