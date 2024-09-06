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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kaz4.composemessanger.R
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

    val userNameLengthError = stringResource(id = R.string.username_length_error)
    val fillAllFieldsError = stringResource(id = R.string.fill_all_fields)

    val context = LocalContext.current

    Surface(
        modifier = Modifier
    ) {
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
                label = { Text(text = stringResource(id = R.string.phone_number)) },
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
                label = { Text(text = stringResource(id = R.string.name)) },
                onValueChange = { newName ->
                    name = newName
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Abc,
                        contentDescription = stringResource(id = R.string.name)
                    )
                }
            )

            UsernameTextField(
                entry = username,
                hint = stringResource(id = R.string.enter_username),
                onChange = { newUsername ->
                    username = newUsername
                    onUsernameChange(newUsername)
                }
            )

            ButtonSign(
                onclick = {
                    if (username.length < 5) {
                        Toast.makeText(context, userNameLengthError, Toast.LENGTH_SHORT).show()
                    } else if (name.isNotBlank()) {
                        onRegister(name, username)
                        isRegistering = true
                    } else {
                        Toast.makeText(context, fillAllFieldsError, Toast.LENGTH_SHORT).show()
                    }
                },
                signInOrSignUp = stringResource(id = R.string.register)
            )

            BottomRouteSign(
                onclick = {
                    navController.navigate("signIn")
                },
                signInOrSignUp = stringResource(id = R.string.sign_in),
                label = stringResource(id = R.string.already_have_account)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegistrationScreen() {
    RegistrationScreenContent(
        phoneNumber = "+79219999999",
        onUsernameChange = {},
        onRegister = { _, _ -> },
        navController = rememberNavController(),
        isLoading = false
    )
}