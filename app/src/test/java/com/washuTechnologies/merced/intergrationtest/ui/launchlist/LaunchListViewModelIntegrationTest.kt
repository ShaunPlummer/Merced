package com.washuTechnologies.merced.intergrationtest.ui.launchlist

import android.accounts.NetworkErrorException
import com.washuTechnologies.merced.api.launches.LaunchesApi
import com.washuTechnologies.merced.ui.launchlist.LaunchListUiState
import com.washuTechnologies.merced.ui.launchlist.LaunchListViewModel
import com.washuTechnologies.merced.util.MockDatasourceHelper
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
        LaunchListViewModel(
            RepositoryHelper.launchesRepository(),
            StandardTestDispatcher(testScheduler)
        ).run {
            val actual: LaunchListUiState = uiState.first()
            assertTrue(actual is LaunchListUiState.Loading)
        }
    }

    @Test
    fun `given the api returns a launch list then it is displayed`() = runTest() {
        val remoteDatasource = MockDatasourceHelper.launchesRemoteDatasource(SampleData.launchList)
        LaunchListViewModel(
            RepositoryHelper.launchesRepository(
                launchesRemoteDatasource = remoteDatasource
            ),
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
    fun `given the internet is not connected then a cached list is returned`() =
        runTest() {
            // given the local cache has less results than the api but the device is not
            // connected to the internet.
            val remoteDatasource = MockDatasourceHelper.mockLaunchesLocalSource(
                SampleData.launchList.take(1).toTypedArray()
            )
            val localDatasource = MockDatasourceHelper.launchesRemoteDatasource(
                launchList = SampleData.launchList.take(2).toTypedArray()
            )
            val connectivity = MockDatasourceHelper.mockConnectivity(false)

            LaunchListViewModel(
                RepositoryHelper.launchesRepository(
                    localDatasource = remoteDatasource,
                    launchesRemoteDatasource = localDatasource,
                    connectivityDatasource = connectivity
                ),
                StandardTestDispatcher(testScheduler)
            ).run {
                // when the UI consumes the state
                val actual: LaunchListUiState = uiState.take(2).last()
                // Then the size matches the local data source
                assertTrue(
                    "No launch list found in result: $actual",
                    (actual as? LaunchListUiState.Success)?.launchList?.size == 1
                )
            }
        }

    @Test
    fun `when a launch list is not available then an error returned`() = runTest() {
        val mockApi = mockk<LaunchesApi> {
            coEvery { getRocketLaunchList() } answers {
                throw NetworkErrorException()
            }
        }

        LaunchListViewModel(
            RepositoryHelper.launchesRepository(
                launchesRemoteDatasource = mockApi,
                connectivityDatasource = MockDatasourceHelper.mockConnectivity(true)
            ),
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