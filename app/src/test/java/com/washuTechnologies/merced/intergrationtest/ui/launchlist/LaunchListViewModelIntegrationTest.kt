package com.washuTechnologies.merced.intergrationtest.ui.launchlist

import android.accounts.NetworkErrorException
import com.washuTechnologies.merced.api.launches.LaunchesApi
import com.washuTechnologies.merced.api.launches.RocketLaunchRepository
import com.washuTechnologies.merced.ui.launchlist.LaunchListUiState
import com.washuTechnologies.merced.ui.launchlist.LaunchListViewModel
import com.washuTechnologies.merced.util.SampleData
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

@ExperimentalCoroutinesApi
class LaunchListViewModelIntegrationTest {

    @Test
    fun `when a launch list is requested then loading is first returned`() = runTest() {
        val mockApi = mockk<LaunchesApi> {
            coEvery { getRocketLaunchList() } returns SampleData.launchList
        }

        LaunchListViewModel(
            RocketLaunchRepository(mockApi),
            StandardTestDispatcher(testScheduler)
        ).run {
            val actual: LaunchListUiState = uiState.first()
            assertTrue(actual is LaunchListUiState.Loading)
        }
    }

    @Test
    fun `when a launch list is available then it is returned`() = runTest() {
        val mockApi = mockk<LaunchesApi> {
            coEvery { getRocketLaunchList() } returns SampleData.launchList
        }

        LaunchListViewModel(
            RocketLaunchRepository(mockApi),
            StandardTestDispatcher(testScheduler)
        ).run {

            val actual: LaunchListUiState = uiState.take(2).last()
            assertTrue(
                "No launch list found in result: $actual",
                (actual as? LaunchListUiState.Success)?.launchList?.isNotEmpty() == true
            )
        }
    }

    @Test
    fun `when a launch list is not available then it is returned`() = runTest() {
        val mockApi = mockk<LaunchesApi> {
            coEvery { getRocketLaunchList() } answers {
                throw NetworkErrorException()
            }
        }

        LaunchListViewModel(
            RocketLaunchRepository(mockApi),
            StandardTestDispatcher(testScheduler)
        ).run {

            val actual: LaunchListUiState = uiState.take(2).last()
            assertTrue(
                "UI state ($actual) is not an error",
                actual is LaunchListUiState.Error
            )
        }
    }
}