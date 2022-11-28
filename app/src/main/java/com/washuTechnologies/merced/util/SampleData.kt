package com.washuTechnologies.merced.util

import com.washuTechnologies.merced.data.launches.model.Links
import com.washuTechnologies.merced.data.launches.model.Patch
import com.washuTechnologies.merced.data.launches.model.Reddit
import com.washuTechnologies.merced.data.launches.model.RocketLaunch

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
                launchDateUTC = "2006-03-24T22:30:00.000Z",
                staticFireDateUTC = "2006-02-24T22:30:00.000Z",
                details = "Engine failure at 33 seconds and loss of vehicle",
                links = Links(
                    patch = Patch(
                        small = "https://images2.imgbox.com/40/e3/GypSkayF_o.png",
                        large = "https://images2.imgbox.com/44/e3/GypSkayF_o.png",
                    ),
                    reddit = Reddit(
                        campaign = "https://www.reddit.com/r/spacex/comments/4jq0ca/falconsat_launch_campaign_thread/",
                        launch = "https://www.reddit.com/r/spacex/comments/4jq0ca/falconsat_launch_campaign_thread/",
                        recovery = "https://www.reddit.com/r/spacex/comments/4jq0ca/falconsat_launch_campaign_thread/",
                        media = "https://www.reddit.com/r/spacex/comments/4jq0ca/falconsat_launch_campaign_thread/",
                    ),
                    presskit = "http://www.spacex.com/sites/spacex/files/falcon1presskit.pdf",
                    article = "https://spaceflightnow.com/2006/03/24/falcon-1-launch-2006-03-24/",
                    wikipedia = "https://en.wikipedia.org/wiki/Falcon_1",
                    videoLink = "0a_00nJ_Y88",
                )
            ),
            RocketLaunch(
                id = "5e9d0d95eda69955f709d1eb",
                flightNumber = 2,
                name = "DemoSat",
                launchDateUTC = "2007-03-21T01:10:00.000Z",
                staticFireDateUTC = "2006-02-24T22:30:00.000Z",
                details = "Successful first stage burn and transition to second stage, maximum altitude 289 km, Premature engine shutdown at T+7 min 30 s, Failed to reach orbit, Failed to recover first stage"
            ),
            RocketLaunch(
                id = "5e9d0d95eda69955f709d1eb",
                flightNumber = 3,
                name = "Trailblazer",
                launchDateUTC = "2008-09-28T23:15:00.000Z",
                staticFireDateUTC = "2006-02-24T22:30:00.000Z",
                details = "Residual stage 1 thrust led to collision between stage 1 and stage 2"
            )
        )
}
