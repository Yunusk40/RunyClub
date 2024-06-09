package com.example.runyclub.navigation

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login")
    object HomeScreen : Screen("home")
    object RunScreen : Screen("runscreen")
    object Account : Screen("account")
}