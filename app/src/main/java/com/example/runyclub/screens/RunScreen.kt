package com.example.runyclub.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.runyclub.widgets.SimpleBottomAppBar

@Composable
fun RunScreen(
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
            text = "Run Screen",
            modifier = Modifier.padding(innerPadding)
        )
    }
}