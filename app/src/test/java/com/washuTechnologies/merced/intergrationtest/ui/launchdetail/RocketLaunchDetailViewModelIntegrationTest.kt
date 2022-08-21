package com.washuTechnologies.merced.intergrationtest.ui.launchdetail

import android.accounts.NetworkErrorException
import androidx.lifecycle.SavedStateHandle
import com.washuTechnologies.merced.data.launches.datasources.LaunchesRemoteDatasource
import com.washuTechnologies.merced.ui.launchdetail.RocketLaunchUiState
import com.washuTechnologies.merced.ui.launchdetail.RocketLaunchViewModel
import com.washuTechnologies.merced.util.MockDatasourceHelper.launchesRemoteDatasource
import com.washuTechnologies.merced.util.RepositoryHelper
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
import org.junit.Assert.assertNull
import org.junit.Test

@ExperimentalCoroutinesApi
class RocketLaunchDetailViewModelIntegrationTest {

    @Test
    fun `when a launch list is requested then loading is first returned`() = runTest() {
        val expectedLaunch = SampleData.rocketLaunch
        val mockApi = launchesRemoteDatasource(expectedLaunch)
        val mockSavedState = mockk<SavedStateHandle> {
            every { get<String>(any()) } returns expectedLaunch.id
        }

        RocketLaunchViewModel(
            RepositoryHelper.launchesRepository(launchesRemoteDatasource = mockApi),
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
        val mockApi = launchesRemoteDatasource(expectedLaunch)
        val mockSavedState = mockk<SavedStateHandle> {
            every { get<String>(any()) } returns expectedLaunch.id
        }

        RocketLaunchViewModel(
            RepositoryHelper.launchesRepository(launchesRemoteDatasource = mockApi),
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
        val mockApi = mockk<LaunchesRemoteDatasource> {
            coEvery { getRocketLaunch(any()) } answers {
                throw NetworkErrorException()
            }
        }
        val mockSavedState = mockk<SavedStateHandle> {
            every { get<String>(any()) } returns expectedLaunch.id
        }

        RocketLaunchViewModel(
            RepositoryHelper.launchesRepository(launchesRemoteDatasource = mockApi),
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

    @Test
    fun `when a launch date is present then it is formatted based on the locale`() = runTest() {
        val expectedLaunch = SampleData.rocketLaunch
        val mockApi = launchesRemoteDatasource(expectedLaunch)
        val mockSavedState = mockk<SavedStateHandle> {
            every { get<String>(any()) } returns expectedLaunch.id
        }

        RocketLaunchViewModel(
            RepositoryHelper.launchesRepository(launchesRemoteDatasource = mockApi),
            mockSavedState,
            StandardTestDispatcher(testScheduler)
        ).run {
            val actual = uiState.take(2).last()
            assertEquals(
                "Fri, 24 March 2006",// 2006-03-24T22:30:00.000Z
                (actual as? RocketLaunchUiState.Success)?.launchDate
            )
        }
    }

    @Test
    fun `when a static fire date is present then it is formatted based on the locale`() =
        runTest() {
            val expectedLaunch = SampleData.rocketLaunch
            val mockApi = launchesRemoteDatasource(expectedLaunch)
            val mockSavedState = mockk<SavedStateHandle> {
                every { get<String>(any()) } returns expectedLaunch.id
            }

            RocketLaunchViewModel(
                RepositoryHelper.launchesRepository(launchesRemoteDatasource = mockApi),
                mockSavedState,
                StandardTestDispatcher(testScheduler)
            ).run {
                val actual = uiState.take(2).last()
                assertEquals(
                    "Fri, 24 February 2006",// 2006-02-24T22:30:00.000Z
                    (actual as? RocketLaunchUiState.Success)?.staticFireDate
                )
            }
        }

    @Test
    fun `when a launch does have a video then the ui state exposes`() = runTest() {
        val expectedLaunch = SampleData.rocketLaunch
        val mockApi = launchesRemoteDatasource(expectedLaunch)
        val mockSavedState = mockk<SavedStateHandle> {
            every { get<String>(any()) } returns expectedLaunch.id
        }

        RocketLaunchViewModel(
            RepositoryHelper.launchesRepository(launchesRemoteDatasource = mockApi),
            mockSavedState,
            StandardTestDispatcher(testScheduler)
        ).run {
            val actual = uiState.take(2).last()
            assertEquals(
                "https://www.youtube.com/watch?v=0a_00nJ_Y88",
                (actual as? RocketLaunchUiState.Success)?.links?.video
            )
        }
    }

    @Test
    fun `when a launch does not have a video then the ui state is empty`() = runTest() {
        val expectedLaunch = SampleData.rocketLaunch.copy(
            links = SampleData.rocketLaunch.links?.copy(
                videoLink = null
            )
        )
        val mockApi = launchesRemoteDatasource(expectedLaunch)
        val mockSavedState = mockk<SavedStateHandle> {
            every { get<String>(any()) } returns expectedLaunch.id
        }

        RocketLaunchViewModel(
            RepositoryHelper.launchesRepository(launchesRemoteDatasource = mockApi),
            mockSavedState,
            StandardTestDispatcher(testScheduler)
        ).run {
            val actual = uiState.take(2).last()
            assertNull(
                (actual as? RocketLaunchUiState.Success)?.links?.video
            )
        }
    }
}
