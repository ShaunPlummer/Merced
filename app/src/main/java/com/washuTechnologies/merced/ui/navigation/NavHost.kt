package com.washuTechnologies.merced.ui.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.washuTechnologies.merced.ui.launchlist.RocketLaunchListScreen

@Composable
fun MercedNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.LaunchList.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.LaunchList.route) {
            RocketLaunchListScreen() {
                navController.navigate(Screen.LaunchDetail.create(it))
            }
        }
        composable(
            route = Screen.LaunchDetail.route,
            arguments = listOf(navArgument(Screen.LaunchDetail.launchIdKey) {
                type = NavType.StringType
            })
        ) {
            Text("Rocket flight number is ${it.arguments?.getInt(Screen.LaunchDetail.launchDetailKey)}")
        }
    }
}
