package com.washuTechnologies.merced.ui.launchdetail

import com.washuTechnologies.merced.api.launches.RocketLaunch
import com.washuTechnologies.merced.util.DateHelper

/**
 * The various UI states of the rocket launch list screen.
 */
sealed class RocketLaunchUiState {

    /**
     * Indicates the rocket launch details could be successfully loaded.
     */
    data class Success(
        val name: String,
        val image: String?,
        val details: String?,
        val launchDate: String
    ) : RocketLaunchUiState()

    /**
     * Indicates an error occurred obtaining rocket launch information.
     */
    object Error : RocketLaunchUiState()

    /**
     * Indicates the passed id is not in the correct format.
     */
    object InvalidId : RocketLaunchUiState()

    /**
     * Indicates the detail screen is still loading.
     */
    object Loading : RocketLaunchUiState()

    companion object {

        /**
         * Convert the launch information to UI state.
         */
        fun fromRocketLaunch(launch: RocketLaunch) = Success(
            name = launch.name,
            image = launch.findImage(),
            details = launch.details,
            launchDate = DateHelper.utcLaunchDateToDisplay(launch.dateUTC)
        )
    }
}
