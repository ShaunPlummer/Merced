package com.washuTechnologies.merced.intergrationtest.ui

import com.washuTechnologies.merced.data.connectivity.ConnectivityRepository
import com.washuTechnologies.merced.domain.connectivity.GetConnectivityStateUseCase
import com.washuTechnologies.merced.ui.AppViewModel
import com.washuTechnologies.merced.util.StandardTestDispatcherRule
import com.washuTechnologies.merced.util.MockDatasourceHelper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AppViewModelTest {

    @get:Rule
    val coroutineRule = StandardTestDispatcherRule()

    @Test
    fun `when connected the app state is correct`() = runTest {
        val connectivityRepo = ConnectivityRepository(
            MockDatasourceHelper.mockConnectivity(true)
        )

        // Given the device is connected to the internet
        AppViewModel(
            GetConnectivityStateUseCase(connectivityRepo)
        ).run {
            // When app state is emitted
            val state = appState.first()

            // Then the connectivity state is correct
            assertTrue(state.isInternetConnected)
        }
    }

    @Test
    fun `when not connected the app state is correct`() = runTest {
        val connectivityRepo = ConnectivityRepository(
            MockDatasourceHelper.mockConnectivity(false)
        )

        // Given the device is not connected to the internet
        AppViewModel(
            GetConnectivityStateUseCase(connectivityRepo)
        ).run {
            // When app state is emitted
            val state = appState.take(2).last()

            // Then the connectivity state is correct
            assertFalse(state.isInternetConnected)
        }
    }
}