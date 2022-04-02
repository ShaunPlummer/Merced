package com.washuTechnologies.merced.intergrationtest.ui.launchdetail

import android.accounts.NetworkErrorException
import androidx.lifecycle.SavedStateHandle
import com.washuTechnologies.merced.api.launches.LaunchesApi
import com.washuTechnologies.merced.api.launches.RocketLaunchRepository
import com.washuTechnologies.merced.ui.launchdetail.RocketLaunchUiState
import com.washuTechnologies.merced.ui.launchdetail.RocketLaunchViewModel
import com.washuTechnologies.merced.util.MockHelper.mockApi
import com.washuTechnologies.merced.util.SampleData
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class RocketLaunchDetailViewModelIntegrationTest {

    @Test
    fun `when a launch list is requested then loading is first returned`() = runTest() {
        val expectedLaunch = SampleData.rocketLaunch
        val mockApi = mockApi(expectedLaunch)
        val mockSavedState = mockk<SavedStateHandle> {
            every { get<String>(any()) } returns expectedLaunch.id
        }

        RocketLaunchViewModel(
            RocketLaunchRepository(mockApi),
            mockSavedState,
            StandardTestDispatcher(testScheduler)
        ).run {
            val actual = uiState.first()
            Assert.assertTrue(actual is RocketLaunchUiState.Loading)
        }
    }

    @Test
    fun `when rocket launch is available then it is returned`() = runTest() {
        val expectedLaunch = SampleData.rocketLaunch
        val mockApi = mockApi(expectedLaunch)
        val mockSavedState = mockk<SavedStateHandle> {
            every { get<String>(any()) } returns expectedLaunch.id
        }

        RocketLaunchViewModel(
            RocketLaunchRepository(mockApi),
            mockSavedState,
            StandardTestDispatcher(testScheduler)
        ).run {
            val actual = uiState.take(2).last()
            assertEquals(
                expectedLaunch.name,
                (actual as? RocketLaunchUiState.Success)?.name
            )
        }
    }

    @Test
    fun `when a launch list is not available then it is returned`() = runTest() {
        val expectedLaunch = SampleData.rocketLaunch
        val mockApi = mockk<LaunchesApi> {
            coEvery { getRocketLaunch(any()) } answers {
                throw NetworkErrorException()
            }
        }
        val mockSavedState = mockk<SavedStateHandle> {
            every { get<String>(any()) } returns expectedLaunch.id
        }

        RocketLaunchViewModel(
            RocketLaunchRepository(mockApi),
            mockSavedState,
            StandardTestDispatcher(testScheduler)
        ).run {
            val actual = uiState.take(2).last()
            Assert.assertTrue(
                "UI state ($actual) is not an error",
                actual is RocketLaunchUiState.Error
            )
        }
    }
}
