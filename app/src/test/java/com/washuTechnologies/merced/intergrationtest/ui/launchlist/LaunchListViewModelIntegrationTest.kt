package com.washuTechnologies.merced.intergrationtest.ui.launchlist

import android.accounts.NetworkErrorException
import com.washuTechnologies.merced.data.launches.datasources.LaunchesRemoteDatasource
import com.washuTechnologies.merced.ui.launchlist.LaunchListUiState
import com.washuTechnologies.merced.ui.launchlist.LaunchListViewModel
import com.washuTechnologies.merced.usecases.GetRocketListUseCase
import com.washuTechnologies.merced.util.MockDatasourceHelper.mockConnectivity
import com.washuTechnologies.merced.util.MockDatasourceHelper.mockLaunchesLocalSource
import com.washuTechnologies.merced.util.MockDatasourceHelper.mockLaunchesRemoteDatasource
import com.washuTechnologies.merced.util.RepositoryHelper
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
        val dispatcher = StandardTestDispatcher(testScheduler)

        LaunchListViewModel(
            GetRocketListUseCase(
                RepositoryHelper.launchesRepository(),
                dispatcher
            ),
            StandardTestDispatcher(testScheduler)
        ).run {
            val actual: LaunchListUiState = uiState.first()
            assertTrue(actual is LaunchListUiState.Loading)
        }
    }

    @Test
    fun `given the api returns a launch list then it is displayed`() = runTest() {
        val dispatcher = StandardTestDispatcher(testScheduler)
        val remoteDatasource = mockLaunchesRemoteDatasource(SampleData.launchList)
        val localDatasource = mockLaunchesLocalSource(emptyArray())
        val launchRepo = RepositoryHelper.launchesRepository(
            remoteDatasource = remoteDatasource,
            localDatasource = localDatasource
        )
        // Given the remote datasource has results
        // and the cache is empty
        LaunchListViewModel(
            GetRocketListUseCase(launchRepo, dispatcher),
            dispatcher
        ).run {
            // When ui state is emitted
            val actual: LaunchListUiState = uiState.take(2).last()
            // Then a launch list is displayed
            assertTrue(
                "No launch list found in result: $actual",
                (actual as? LaunchListUiState.Success)?.launchList?.isNotEmpty() == true
            )
        }
    }

    @Test
    fun `given the internet is not connected then a cached list is returned`() =
        runTest() {
            val dispatcher = StandardTestDispatcher(testScheduler)
            val localDatasource = mockLaunchesLocalSource(
                SampleData.launchList
            )
            val connectivity = mockConnectivity(false)
            val launchRepo = RepositoryHelper.launchesRepository(
                remoteDatasource = mockk(),
                localDatasource = localDatasource,
                connectivityDatasource = connectivity
            )

            // Given the local datasource has launch information
            // and the internet is not connected
            LaunchListViewModel(
                GetRocketListUseCase(launchRepo, dispatcher),
                dispatcher
            ).run {
                // When ui state is emitted
                val actual = uiState.take(2).last()
                // Then the size matches the local data source
                assertTrue(
                    "No launch list found in result: $actual",
                    actual is LaunchListUiState.Success
                )
            }
        }

    @Test
    fun `when a network error occurs a cached list is returned`() = runTest() {
        val dispatcher = StandardTestDispatcher(testScheduler)
        val remoteDatasource = mockk<LaunchesRemoteDatasource> {
            coEvery { getRocketLaunchList() } answers {
                throw NetworkErrorException()
            }
        }
        val localDatasource = mockLaunchesLocalSource(SampleData.launchList)
        val launchRepo = RepositoryHelper.launchesRepository(
            remoteDatasource = remoteDatasource,
            localDatasource = localDatasource,
            connectivityDatasource = mockConnectivity(true)
        )

        // Given there is no local list and the internet is  not connected
        LaunchListViewModel(
            GetRocketListUseCase(launchRepo, dispatcher),
            dispatcher
        ).run {
            // When ui state is emitted
            val actual = uiState.take(2).last()
            // Then it is an error
            assertTrue(
                "UI state ($actual) is not an error",
                actual is LaunchListUiState.Success
            )
        }
    }

    @Test
    fun `when the cache is out of date it is updated`() = runTest() {
        val dispatcher = StandardTestDispatcher(testScheduler)
        val remoteDatasource = mockLaunchesRemoteDatasource(SampleData.launchList)
        val localDatasource = mockLaunchesLocalSource(
            SampleData.launchList.first()
        )
        val launchRepo = RepositoryHelper.launchesRepository(
            remoteDatasource = remoteDatasource,
            localDatasource = localDatasource,
            connectivityDatasource = mockConnectivity(true)
        )

        // Given there is cached data but the remote data source has an update
        LaunchListViewModel(
            GetRocketListUseCase(launchRepo, dispatcher),
            dispatcher
        ).run {
            // When ui state is emitted
            val actual: LaunchListUiState = uiState.take(2).last()
            // Then the launch list is up to date
            assertTrue(
                "Incorrect size launch list: $actual",
                (actual as? LaunchListUiState.Success)?.launchList?.size == SampleData.launchList.size
            )
        }
    }
}