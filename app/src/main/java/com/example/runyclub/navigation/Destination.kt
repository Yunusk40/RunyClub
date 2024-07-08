package com.example.runyclub.navigation

import androidx.navigation.NavController
import androidx.navigation.navDeepLink

// Defines a sealed class for navigation destinations within the app.
sealed class Destination(val route: String) {

    // Represents the onboarding screen destination with a method to navigate to the home screen.
    object OnBoardingDestination : Destination("on_boarding") {
        fun navigateToHome(navController: NavController) {
            navController.navigate(BottomNavDestination.Home.route) {
                // Clears the onboarding screen from the back stack when navigating to the home screen
                popUpTo(route) {
                    inclusive = true
                }
                // Ensures no duplicate instances of the home screen are created.
                launchSingleTop = true
            }
        }
    }

    // Represents the current run screen destination with deep link support.
    object CurrentRun : Destination("current_run") {
        val currentRunUriPattern = "https://RunyClub.com/$route"
        val deepLinks = listOf(
            navDeepLink {
                uriPattern = currentRunUriPattern
            }
        )
    }

    // Placeholder for the run statistics destination. Note: 'data object' is not a valid Kotlin syntax.
    data object RunStats : Destination("run_stats")

    // Companion object to provide a global navigation method to the current run screen.
    companion object {
        fun navigateToCurrentRunScreen(navController: NavController) {
            navController.navigate(CurrentRun.route)
        }
    }

}
