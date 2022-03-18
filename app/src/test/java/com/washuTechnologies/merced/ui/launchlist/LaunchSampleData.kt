package com.washuTechnologies.merced.ui.launchlist

import com.washuTechnologies.merced.api.launches.RocketLaunch

/**
 * Sample rocket launch data for use in testing.
 */
object LaunchSampleData {

    /**
     * Sample list of rocket launches.
     */
    val sampleLaunches: Array<RocketLaunch>
        get() = arrayOf(
            RocketLaunch(1, "FalconSat", "2006-03-24T22:30:00.000Z"),
            RocketLaunch(2, "DemoSat", "2007-03-21T01:10:00.000Z"),
            RocketLaunch(3, "Trailblazer", "2008-09-28T23:15:00.000Z")
        )
}
