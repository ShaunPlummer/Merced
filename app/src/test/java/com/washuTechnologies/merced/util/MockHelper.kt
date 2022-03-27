package com.washuTechnologies.merced.util

import com.washuTechnologies.merced.api.launches.LaunchesApi
import com.washuTechnologies.merced.api.launches.RocketLaunch
import io.mockk.coEvery
import io.mockk.mockk

object MockHelper {

    fun mockApi(rocketLaunch: RocketLaunch) = mockk<LaunchesApi> {
        coEvery { getRocketLaunch(rocketLaunch.id) } returns rocketLaunch
    }

    fun mockApi(launchList: Array<RocketLaunch>) = mockk<LaunchesApi> {
        coEvery { getRocketLaunchList() } returns launchList
    }
}
