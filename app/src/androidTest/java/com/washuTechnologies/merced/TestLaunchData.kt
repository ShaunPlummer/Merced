package com.washuTechnologies.merced

import com.washuTechnologies.merced.data.launches.model.RocketLaunch

/**
 * Sample data for use in testing.
 */
object TestLaunchData {
    val list = arrayOf(
        RocketLaunch(
            id = "6243aec2af52800c6e91925d",
            flightNumber = 188,
            name = "USSF-44",
            dateUTC = "2022-11-01T13:41:00.000Z",
            staticFireDateUTC = null
        ),
        RocketLaunch(
            id = "5eb87cd9ffd86e000604b32a",
            flightNumber = 1,
            name = "FalconSat",
            dateUTC = "2006-03-24T22:30:00.000Z",
            staticFireDateUTC = null
        )
    )
}
