package com.example.runyclub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.example.runyclub.navigation.Navigation
import com.example.runyclub.roomdatabase.AppDatabase
import com.example.runyclub.roomdatabase.UserRepository
import com.example.runyclub.ui.theme.RunyClubTheme
import com.example.runyclub.viewmodels.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var userRepository: UserRepository
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            val database = AppDatabase.getDatabase(this@MainActivity)
            userRepository = UserRepository(database.userDao())
            loginViewModel = LoginViewModel(userRepository)
        }
        enableEdgeToEdge()
        setContent {
            val requestPermissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                // handle permission result
            }
            RunyClubTheme {
                Navigation(loginViewModel, requestPermissionLauncher)
            }
        }
    }
}
