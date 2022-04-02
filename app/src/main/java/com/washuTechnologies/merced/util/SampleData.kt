package com.washuTechnologies.merced.util

import com.washuTechnologies.merced.api.launches.RocketLaunch

/**
 * Mock data for use in compose preview annotations.
 */
object SampleData {

    /**
     * Information about a single rocket launch.
     */
    val rocketLaunch: RocketLaunch
        get() = launchList.first()

    /**
     * A list of rocket launches.
     */
    val launchList: Array<RocketLaunch>
        @SuppressWarnings("MaxLineLength")
        get() = arrayOf(
            RocketLaunch(
                id = "5eb87cd9ffd86e000604b32a",
                flightNumber = 1,
                name = "FalconSat",
                dateUTC = "2006-03-24T22:30:00.000Z",
                details = "Engine failure at 33 seconds and loss of vehicle"
            ),
            RocketLaunch(
                id = "5e9d0d95eda69955f709d1eb",
                flightNumber = 2,
                name = "DemoSat",
                dateUTC = "2007-03-21T01:10:00.000Z",
                details = "Successful first stage burn and transition to second stage, maximum altitude 289 km, Premature engine shutdown at T+7 min 30 s, Failed to reach orbit, Failed to recover first stage"
            ),
            RocketLaunch(
                id = "5e9d0d95eda69955f709d1eb",
                flightNumber = 3,
                name = "Trailblazer",
                dateUTC = "2008-09-28T23:15:00.000Z",
                details = "Residual stage 1 thrust led to collision between stage 1 and stage 2"
            )
        )
}
