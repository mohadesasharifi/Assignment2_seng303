package nz.ac.canterbury.seng303.lab2.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import nz.ac.canterbury.seng303.lab2.models.User
import nz.ac.canterbury.seng303.lab2.viewmodels.UserViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(userViewModel: UserViewModel, navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(8.dp))
        }

        Button(
            onClick = {
                if (password != confirmPassword) {
                    errorMessage = "Passwords do not match!"
                } else if (username.isEmpty() || password.isEmpty()) {
                    errorMessage = "All fields must be filled!"
                } else {
                    val user =
                        User(id = username.hashCode(), username = username, password = password)
                    userViewModel.viewModelScope.launch {
                        val existing_user = userViewModel.getUserByUsername(username)
                        if (existing_user != null) {
                            errorMessage = "User already exists!"
                            return@launch
                        }
                        val registerDeferred = userViewModel.registerUser(user)
                        registerDeferred.await()
                        val loginSuccessful = userViewModel.loginUser(username, password)
                        if (loginSuccessful) {
                            Toast.makeText(context, "Sign up successful", Toast.LENGTH_SHORT).show()
                            navController.navigate("Home") {
                                popUpTo("Home") { inclusive = true }
                            }
                        } else {
                            errorMessage = "Couldn't log in user after registration"
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }

    }
}

