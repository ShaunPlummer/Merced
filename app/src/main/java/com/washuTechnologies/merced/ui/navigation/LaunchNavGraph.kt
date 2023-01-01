package com.washuTechnologies.merced.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.washuTechnologies.merced.ui.launchdetail.RocketLaunchDetailScreen
import com.washuTechnologies.merced.ui.launchdetail.RocketLaunchViewModel
import com.washuTechnologies.merced.ui.launchlist.LaunchListViewModel
import com.washuTechnologies.merced.ui.launchlist.RocketLaunchListScreen

const val LAUNCH_LIST_ROUTE = "launchList"
private const val LAUNCH_DETAIL_ROUTE = "launchDetail/{launchID}"
private const val LAUNCH_DETAIL_KEY = "launchID"


/**
 * Composable navigation destination for a list of all rocket launches.
 */
fun NavGraphBuilder.rocketLaunchListScreen(
    onLaunchSelected: (String) -> Unit = {}
) {
    composable(route = LAUNCH_LIST_ROUTE) {
        val viewModel: LaunchListViewModel = hiltViewModel()
        val list = viewModel.uiState.collectAsState()
        RocketLaunchListScreen(launchList = list.value, onLaunchSelected = onLaunchSelected)
    }
}

/**
 * Parses the given [SavedStateHandle] for arguments used by the launch rocket detail screen.
 */
internal class RocketLaunchArgs(savedStateHandle: SavedStateHandle) {
    /**
     * The provided launch ID or a blank string if one could not be found.
     */
    val launchId: String = savedStateHandle.get<String>(
    LAUNCH_DETAIL_KEY
    ) ?: ""
}

/**
 * Composable navigation destination for a particular rocket launch.
 */
fun NavGraphBuilder.rocketDetailScreen() {
    composable(
        route = LAUNCH_DETAIL_ROUTE,
        arguments = listOf(navArgument(LAUNCH_DETAIL_KEY) {
            type = NavType.StringType
        })
    ) {
        val viewModel: RocketLaunchViewModel = hiltViewModel()
        val state = viewModel.uiState.collectAsState()
        RocketLaunchDetailScreen(
            rocketLaunchState = state.value
        )
    }
}

/**
 * Navigate to a screen showing detailed information about the specified [launchId].
 */
fun NavController.navigateToLaunch(launchId: String) {
    navigate("launchDetail/$launchId")
}