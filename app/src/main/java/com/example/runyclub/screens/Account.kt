package com.example.runyclub.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.runyclub.viewmodels.AccountViewModel
import com.example.runyclub.viewmodels.AccountViewModelFactory
import com.example.runyclub.roomdatabase.AppDatabase
import com.example.runyclub.roomdatabase.repository.UserRepository
import com.example.runyclub.widgets.SimpleBottomAppBar

@Composable
fun Account(
    navController: NavController,
) {
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val userRepository = UserRepository(database.userDao())
    val viewModel: AccountViewModel = viewModel(factory = AccountViewModelFactory(userRepository))

    val user by viewModel.user.collectAsState()

    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }

    LaunchedEffect(user) {
        user?.let {
            weight = it.weight.toString()
            height = it.height.toString()
        }
    }

    Scaffold(
        bottomBar = {
            SimpleBottomAppBar(navController = navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text("Account", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text("Weight (kg)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = height,
                onValueChange = { height = it },
                label = { Text("Height (cm)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val weightValue = weight.toFloatOrNull()
                    val heightValue = height.toFloatOrNull()
                    if (weightValue != null && heightValue != null) {
                        viewModel.updateUser(user?.username ?: "", weightValue, heightValue)
                        Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Please enter valid values", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Update")
            }
        }
    }
}
