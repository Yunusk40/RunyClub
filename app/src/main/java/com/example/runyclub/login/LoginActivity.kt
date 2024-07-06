import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.runyclub.roomdatabase.AppDatabase
import com.example.runyclub.roomdatabase.repository.UserRepository
import com.example.runyclub.screens.LoginScreen
import com.example.runyclub.viewmodels.LoginViewModel
import com.example.runyclub.viewmodels.LoginViewModelFactory

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            val context = LocalContext.current
            val database = AppDatabase.getDatabase(context)
            val userRepository = UserRepository(database.userDao())
            val viewModel = ViewModelProvider(this, LoginViewModelFactory(userRepository)).get(LoginViewModel::class.java)
            LoginScreen(navController = rememberNavController(), viewModel = viewModel)

            Column(modifier = Modifier.padding(16.dp)) {
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(autoCorrect = false),
                    keyboardActions = KeyboardActions.Default
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    viewModel.loginUser(username, password) { isLoggedIn ->
                        if (isLoggedIn) {
                            // Navigate to the next screen or show success
                            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                        } else {
                            // Show error message
                            Toast.makeText(context, "Invalid credentials", Toast.LENGTH_SHORT).show()
                        }
                    }
                }) {
                    Text("Login")
                }
            }
        }
    }
}
