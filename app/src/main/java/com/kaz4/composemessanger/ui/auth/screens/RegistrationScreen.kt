package com.kaz4.composemessanger.ui.auth.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kaz4.composemessanger.ui.auth.components.BottomRouteSign
import com.kaz4.composemessanger.ui.auth.components.ButtonSign
import com.kaz4.composemessanger.ui.auth.components.UsernameTextField
import com.kaz4.composemessanger.ui.theme.spacing

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    RegistrationScreenContent(
        phoneNumber = "+7 (999) 999-99-99", //todo
        onUsernameChange = {

        },
        onRegister = { _, _ ->
            navController.navigate("chatList")
        },
        modifier = modifier,
        navController = navController
    )
}

@Composable
fun RegistrationScreenContent(
    phoneNumber: String,
    onUsernameChange: (String) -> Unit,
    onRegister: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    var username by remember { mutableStateOf("") }
    var isRegistering by remember { mutableStateOf(false) }

    val context = LocalContext.current

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
                if (username.isNotBlank()) {
                    onRegister(phoneNumber, username)
                    Log.d("AAA", "username: $username, phone: $phoneNumber")
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

@Preview(showBackground = true)
@Composable
fun PreviewRegistrationScreen() {
    RegistrationScreenContent(
        phoneNumber = "+7 (999) 999-99-99",
        onUsernameChange = {},
        onRegister = { _, _ -> },
        navController = rememberNavController()
    )
}