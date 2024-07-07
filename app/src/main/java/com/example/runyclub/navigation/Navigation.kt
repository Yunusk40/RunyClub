package com.example.runyclub.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.runyclub.ui.screens.currentrun.RunScreen
import com.example.runyclub.ui.screens.login.LoginScreen
import com.example.runyclub.ui.screens.profile.ProfileScreen
import com.example.runyclub.ui.screens.runstats.RunStatsScreen

@Composable
fun Navigation(
    navController: NavHostController,
) {
    SetupNavGraph(
        navController = navController,
    )
}

@Composable
private fun SetupNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavDestination.Home.route
    ) {
        homeNavigation(navController)

        composable(route = BottomNavDestination.Profile.route) {
            ProfileScreen()
        }

        composable(
            route = BottomNavDestination.CurrentRun.route,
            deepLinks = Destination.CurrentRun.deepLinks) {
            RunScreen(navController)
        }

        composable(
            route = Destination.OnBoardingDestination.route) {
            LoginScreen(navController = navController)
        }

        composable(
            route = Destination.RunStats.route) {
            RunStatsScreen(
                navigateUp = { navController.navigateUp() }
            )
        }
    }

}