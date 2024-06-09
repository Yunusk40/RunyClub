package com.example.runyclub.widgets

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.movieappmad24.widgets.BottomBarScreen

@Composable
fun SimpleBottomAppBar(navController: NavController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.RunScreen,
        BottomBarScreen.Account
        )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        screens.forEach { screen ->
            NavigationBarItem(label = { Text(screen.title) },
                selected = currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true,
                onClick = { navController.navigate(screen.route) },
                icon = { Icon(
                    imageVector = screen.icon,
                    contentDescription = screen.title
                )
                }
            )
        }
    }
}