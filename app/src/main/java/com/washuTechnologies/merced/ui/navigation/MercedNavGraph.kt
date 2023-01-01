package com.washuTechnologies.merced.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

/**
 * Main navigation graph for the application and entry point.
 */
@Composable
fun MercedNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = LAUNCH_LIST_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        rocketLaunchListScreen(
            onLaunchSelected = { navController.navigateToLaunch(it) }
        )
        rocketDetailScreen()
    }
}
