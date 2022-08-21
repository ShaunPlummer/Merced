package com.washuTechnologies.merced.unittest.ui.launchlist

import com.washuTechnologies.merced.data.Result
import com.washuTechnologies.merced.data.launches.RocketLaunchRepository
import com.washuTechnologies.merced.ui.launchlist.LaunchListUiState
import com.washuTechnologies.merced.ui.launchlist.LaunchListViewModel
import com.washuTechnologies.merced.util.SampleData
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

@ExperimentalCoroutinesApi
class LaunchListViewModelUnitTest {

    @Test
    fun `when a launch list is available then it is returned`() = runTest() {
        val mockRepository = mockk<RocketLaunchRepository> {
            every { getLaunchList() } returns flow {
                emit(Result.Success(SampleData.launchList))
            }
        }

        LaunchListViewModel(
            mockRepository,
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
        val mockRepository = mockk<RocketLaunchRepository> {
            every { getLaunchList() } returns flow {
                emit(Result.Error)
            }
        }

        LaunchListViewModel(
            mockRepository,
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
