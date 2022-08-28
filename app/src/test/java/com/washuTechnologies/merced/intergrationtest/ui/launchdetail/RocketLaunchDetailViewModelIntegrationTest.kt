package com.washuTechnologies.merced.intergrationtest.ui.launchdetail

import android.accounts.NetworkErrorException
import androidx.lifecycle.SavedStateHandle
import com.washuTechnologies.merced.data.launches.datasources.LaunchesRemoteDatasource
import com.washuTechnologies.merced.ui.launchdetail.RocketLaunchUiState
import com.washuTechnologies.merced.ui.launchdetail.RocketLaunchViewModel
import com.washuTechnologies.merced.util.MockDatasourceHelper.mockConnectivity
import com.washuTechnologies.merced.util.MockDatasourceHelper.mockLaunchesLocalSource
import com.washuTechnologies.merced.util.MockDatasourceHelper.mockLaunchesRemoteDatasource
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
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

@ExperimentalCoroutinesApi
class RocketLaunchDetailViewModelIntegrationTest {

    @Test
    fun `when a launch list is requested then loading is first returned`() = runTest() {
        val expectedLaunch = SampleData.rocketLaunch
        val remoteDatasource = mockLaunchesRemoteDatasource(expectedLaunch)

        RocketLaunchViewModel(
            RepositoryHelper.launchesRepository(remoteDatasource = remoteDatasource),
            mockSavedStateHandle(expectedLaunch.id),
            StandardTestDispatcher(testScheduler)
        ).run {
            val actual = uiState.first()
            assertTrue(actual is RocketLaunchUiState.Loading)
        }
    }

    @Test
    fun `given the api returns a rocket launch then it is displayed`() = runTest() {
        val expectedLaunch = SampleData.rocketLaunch
        val remoteDatasource = mockLaunchesRemoteDatasource(expectedLaunch)
        val localDatasource = mockLaunchesLocalSource(emptyArray())
        val launchRepo = RepositoryHelper.launchesRepository(
            remoteDatasource = remoteDatasource,
            localDatasource = localDatasource
        )
        // Given the remote datasource has results
        // and the cache is empty
        RocketLaunchViewModel(
            launchRepo,
            mockSavedStateHandle(expectedLaunch.id),
            StandardTestDispatcher(testScheduler)
        ).run {
            // When ui state is emitted
            val actual = uiState.take(2).last()
            // Then a launch list is displayed
            assertEquals(
                expectedLaunch.name,
                (actual as? RocketLaunchUiState.Success)?.name
            )
        }
    }

    @Test
    fun `given the internet is not connected then a cached launch is returned`() = runTest() {
        val expectedLaunch = SampleData.rocketLaunch
        val localDatasource = mockLaunchesLocalSource(
            expectedLaunch
        )
        val connectivity = mockConnectivity(false)
        val launchRepo = RepositoryHelper.launchesRepository(
            remoteDatasource = mockk(),
            localDatasource = localDatasource,
            connectivityDatasource = connectivity
        )

        // Given the local datasource has launch information
        // and the internet is not connected
        RocketLaunchViewModel(
            launchRepo,
            mockSavedStateHandle(expectedLaunch.id),
            StandardTestDispatcher(testScheduler)
        ).run {
            // When ui state is emitted
            val actual = uiState.take(2).last()
            // Then the size matches the local data source
            assertTrue(
                "UI state ($actual) is not an error",
                actual is RocketLaunchUiState.Success
            )
        }
    }

    @Test
    fun `when launch information is not available then an error is returned`() = runTest() {
        val expectedLaunch = SampleData.rocketLaunch
        val remoteDatasource = mockk<LaunchesRemoteDatasource> {
            coEvery { getRocketLaunch(any()) } answers {
                throw NetworkErrorException()
            }
        }
        val launchRepo = RepositoryHelper.launchesRepository(
            remoteDatasource = remoteDatasource,
            connectivityDatasource = mockConnectivity(true)
        )
        // Given the device is connected
        // and the api returns an error
        RocketLaunchViewModel(
            launchRepo,
            mockSavedStateHandle(expectedLaunch.id),
            StandardTestDispatcher(testScheduler)
        ).run {
            // When ui state is emitted
            val actual = uiState.take(2).last()
            // Then it is an error
            assertTrue(
                "UI state ($actual) is not an error",
                actual is RocketLaunchUiState.Error
            )
        }
    }

    @Test
    fun `when the cache is out of date it is updated`() = runTest() {
        val expectedLaunch = SampleData.rocketLaunch
        val localDatasource = mockLaunchesLocalSource(expectedLaunch)
        val remoteDatasource = mockLaunchesRemoteDatasource(
            expectedLaunch.copy(
                name = "Updated Name"
            )
        )
        val launchRepo = RepositoryHelper.launchesRepository(
            remoteDatasource = remoteDatasource,
            localDatasource = localDatasource
        )
        // Given the device is connected
        // and the api returns an error
        RocketLaunchViewModel(
            launchRepo,
            mockSavedStateHandle(expectedLaunch.id),
            StandardTestDispatcher(testScheduler)
        ).run {
            // When ui state is emitted
            val actual = uiState.take(2).last()
            // Then it is an error
            assertEquals(
                "Updated Name",
                (actual as RocketLaunchUiState.Success).name
            )
        }
    }

    @Test
    fun `when a launch date is present then it is formatted based on the locale`() = runTest() {
        val expectedLaunch = SampleData.rocketLaunch
        val remoteDatasource = mockLaunchesRemoteDatasource(expectedLaunch)

        RocketLaunchViewModel(
            RepositoryHelper.launchesRepository(remoteDatasource = remoteDatasource),
            mockSavedStateHandle(expectedLaunch.id),
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
            val remoteDatasource = mockLaunchesRemoteDatasource(expectedLaunch)

            RocketLaunchViewModel(
                RepositoryHelper.launchesRepository(remoteDatasource = remoteDatasource),
                mockSavedStateHandle(expectedLaunch.id),
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
        val remoteDatasource = mockLaunchesRemoteDatasource(expectedLaunch)

        RocketLaunchViewModel(
            RepositoryHelper.launchesRepository(remoteDatasource = remoteDatasource),
            mockSavedStateHandle(expectedLaunch.id),
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
        val remoteDatasource = mockLaunchesRemoteDatasource(expectedLaunch)

        RocketLaunchViewModel(
            RepositoryHelper.launchesRepository(remoteDatasource = remoteDatasource),
            mockSavedStateHandle(expectedLaunch.id),
            StandardTestDispatcher(testScheduler)
        ).run {
            val actual = uiState.take(2).last()
            assertNull(
                (actual as? RocketLaunchUiState.Success)?.links?.video
            )
        }
    }

    private fun mockSavedStateHandle(launchId: String) = mockk<SavedStateHandle> {
        every { get<String>(any()) } returns launchId
    }
}
