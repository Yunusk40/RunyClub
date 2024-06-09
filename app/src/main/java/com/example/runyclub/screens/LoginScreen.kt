package com.example.runyclub.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.runyclub.viewmodels.LoginViewModel

//login UI
@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showFields by remember { mutableStateOf(false) }
    var currentAction by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("RunyClub", style = TextStyle(fontSize = 30.sp))
        Spacer(modifier = Modifier.height(24.dp))
        if (showFields) {
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(autoCorrect = false)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(autoCorrect = false)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    when (currentAction) {
                        "register" -> {
                            if (username.isNotBlank() && password.isNotBlank()) {
                                viewModel.registerUser(username, password)
                                Toast.makeText(context, "Registered successfully", Toast.LENGTH_SHORT).show()
                                navController.navigate("home")
                            } else {
                                Toast.makeText(context, "Please fill in both fields", Toast.LENGTH_SHORT).show()
                            }
                        }
                        "login" -> {
                            viewModel.loginUser(username, password) { isLoggedIn ->
                                if (isLoggedIn) {
                                    navController.navigate("home") {
                                        popUpTo("login") {
                                            inclusive = true
                                        }
                                    }
                                } else {
                                    Toast.makeText(context, "Invalid credentials", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit")
            }
        } else {
            Button(
                onClick = {
                    showFields = true
                    currentAction = "register"
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    showFields = true
                    currentAction = "login"
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }
        }
    }
}



private fun NavHostController.onLoginSuccess() {
    this.navigate("home") {
        popUpTo("login") {
            inclusive = true // This clears the login from the back stack
        }
    }
}



