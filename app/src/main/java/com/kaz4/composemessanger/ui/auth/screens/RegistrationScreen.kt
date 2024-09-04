package com.kaz4.composemessanger.ui.auth.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kaz4.composemessanger.ui.auth.AuthIntent
import com.kaz4.composemessanger.ui.auth.AuthViewModel
import com.kaz4.composemessanger.ui.auth.components.BottomRouteSign
import com.kaz4.composemessanger.ui.auth.components.ButtonSign
import com.kaz4.composemessanger.ui.auth.components.UsernameTextField
import com.kaz4.composemessanger.ui.theme.spacing

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel(),
    phoneNumber: String
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    RegistrationScreenContent(
        phoneNumber = phoneNumber,
        isLoading = state.isLoading,
        onUsernameChange = { username ->

        },
        onRegister = { name, username ->
            viewModel.processIntent(AuthIntent.RegisterUser(phoneNumber, name, username))
            if (state.errorMessage == null) {
                navController.navigate("chatList")
            } else {
                Toast.makeText(context, state.errorMessage, Toast.LENGTH_SHORT).show()
            }
        },
        modifier = modifier,
        navController = navController
    )
}

@Composable
fun RegistrationScreenContent(
    phoneNumber: String,
    isLoading: Boolean,
    onUsernameChange: (String) -> Unit,
    onRegister: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    var username by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var isRegistering by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Surface(
        modifier = Modifier
    )
    {
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.large),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = phoneNumber,
                label = { Text(text = "Phone Number") },
                onValueChange = {},
                readOnly = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = null
                    )
                }
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                label = { Text(text = "Name") },
                onValueChange = { newName ->
                    name = newName
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Abc,
                        contentDescription = "name"
                    )
                }
            )

            UsernameTextField(
                entry = username,
                hint = "Enter your username",
                onChange = { newUsername ->
                    username = newUsername
                    onUsernameChange(newUsername)
                }
            )

            ButtonSign(
                onclick = {
                    if (username.length < 5) {
                        Toast.makeText(context, "Username must be at least 5 characters long", Toast.LENGTH_SHORT).show()
                    } else if (name.isNotBlank()) {
                        onRegister(name, username)
                        isRegistering = true
                    } else {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    }
                },
                signInOrSignUp = "Register"
            )

            BottomRouteSign(
                onclick = {
                    navController.navigate("signIn")
                },
                signInOrSignUp = "Sign In",
                label = "Already have an account?"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegistrationScreen() {
    RegistrationScreenContent(
        phoneNumber = "+7 (999) 999-99-99",
        onUsernameChange = {},
        onRegister = { _, _ -> },
        navController = rememberNavController(),
        isLoading = false
    )
}