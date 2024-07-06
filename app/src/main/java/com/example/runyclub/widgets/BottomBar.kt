package com.example.runyclub.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.runyclub.navigation.Screen

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home: BottomBarScreen(
        route = Screen.HomeScreen.route,
        title = "Home",
        icon = Icons.Filled.Home
    )

    object RunScreen: BottomBarScreen(
        route = Screen.RunScreen.route,
        title = "RunScreen",
        icon = Icons.Filled.PlayArrow
    )

    object Account: BottomBarScreen(
        route = Screen.Account.route,
        title = "Account",
        icon = Icons.Filled.AccountCircle
    )
}