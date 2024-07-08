package com.example.runyclub.ui.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.runyclub.common.compose.compositonLocal.LocalScaffoldBottomPadding
import com.example.runyclub.navigation.BottomNavDestination
import com.example.runyclub.navigation.Destination
import com.example.runyclub.navigation.Navigation
import com.example.runyclub.theme.AppTheme

@Composable
private fun MainScreenPreview() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MainScreen(rememberNavController())
        }
    }
}

@Composable
fun MainScreen(
    navHostController: NavHostController,
    viewModel: HomeScreen = hiltViewModel()
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()

    var shouldShowBottomNav by rememberSaveable { mutableStateOf(false) }
    var hideBottomItems by rememberSaveable { mutableStateOf(true) }
    val doesUserExist by viewModel.doesUserExist.collectAsStateWithLifecycle()

    hideBottomItems = when (navBackStackEntry?.destination?.route) {
        Destination.CurrentRun.route -> true
        Destination.OnBoardingDestination.route -> true
        Destination.RunStats.route -> true
        else -> false
    }

    LaunchedEffect(hideBottomItems) {
        shouldShowBottomNav = !hideBottomItems
    }

    Scaffold(
        bottomBar = {
            if (shouldShowBottomNav && doesUserExist == true) {
                BottomBar(navController = navHostController)
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            CompositionLocalProvider(
                LocalScaffoldBottomPadding provides it.calculateBottomPadding()
            ) {
                Navigation(navHostController)
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavController
) {
    BottomNavBar(
        navController = navController,
        items = listOf(
            BottomNavDestination.Home,
            BottomNavDestination.Profile,
            BottomNavDestination.CurrentRun
        ),
        modifier = Modifier.fillMaxWidth() // Ensure the BottomNavBar fills the width
    )
}

@Composable
private fun BottomNavBar(
    navController: NavController,
    modifier: Modifier = Modifier,
    items: List<BottomNavDestination>
) {
    BottomNavigation(
        modifier = modifier,
        backgroundColor = MaterialTheme.colorScheme.surface,
        elevation = 0.dp,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach {
            BottomNavItem(it, navController, currentDestination)
        }
    }
}

@Composable
private fun RowScope.BottomNavItem(
    item: BottomNavDestination,
    navController: NavController,
    currentDestination: NavDestination?
) {
    BottomNavigationItem(
        icon = {
            Icon(imageVector = item.getIconVector(), contentDescription = "")
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == item.route
        } == true,
        onClick = {
            if (navController.currentDestination?.route != item.route) {
                navController.navigate(item.route) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    // Ensuring launchSingleTop is used appropriately
                    launchSingleTop = true
                    // Removing restoreState to prevent IllegalStateException
                    // restoreState = true
                }
            }
        },
        unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
        selectedContentColor = MaterialTheme.colorScheme.primary,
    )
}
