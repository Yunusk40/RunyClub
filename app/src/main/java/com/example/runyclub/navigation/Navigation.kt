package com.example.runyclub.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.runyclub.screens.Account
import com.example.runyclub.screens.HomeScreen
import com.example.runyclub.screens.LoginScreen
import com.example.runyclub.screens.RunScreen
import com.example.runyclub.viewmodels.LoginViewModel

@Composable
fun Navigation(viewModel: LoginViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController = navController, viewModel = viewModel)
        }
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("runscreen") {
            RunScreen(navController = navController)
        }
        composable("account") {
            Account(navController = navController)
        }
    }
}