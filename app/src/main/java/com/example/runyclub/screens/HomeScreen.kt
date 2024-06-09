package com.example.runyclub.screens
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.runyclub.widgets.SimpleBottomAppBar
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(
    navController: NavController,
) {
    Scaffold (
        bottomBar = {
            SimpleBottomAppBar(
                navController = navController
            )
        }
    ){ innerPadding ->
        Text(
            text = "Home Screen",
            modifier = Modifier.padding(innerPadding)
        )
    }
}