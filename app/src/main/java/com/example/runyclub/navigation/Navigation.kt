package com.example.runyclub.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.runyclub.screens.Account
import com.example.runyclub.screens.HomeScreen
import com.example.runyclub.screens.LoginScreen
import com.example.runyclub.screens.RunScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController = navController)
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