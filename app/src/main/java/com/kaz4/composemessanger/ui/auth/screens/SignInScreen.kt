package com.kaz4.composemessanger.ui.auth.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kaz4.composemessanger.R
import com.kaz4.composemessanger.ui.auth.AuthIntent
import com.kaz4.composemessanger.ui.auth.AuthState
import com.kaz4.composemessanger.ui.auth.AuthViewModel
import com.kaz4.composemessanger.ui.auth.components.BottomRouteSign
import com.kaz4.composemessanger.ui.auth.components.ButtonSign
import com.kaz4.composemessanger.ui.auth.components.CodeInputField
import com.kaz4.composemessanger.ui.auth.components.PhoneNumberInputField
import com.kaz4.composemessanger.ui.theme.ComposeMessangerTheme
import com.kaz4.composemessanger.ui.theme.spacing

@Composable
fun AuthorizationScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val state by authViewModel.state.collectAsState()

    AuthorizationScreenContent(
        state = state,
        onPhoneNumberSubmit = { phoneNumber ->
            authViewModel.processIntent(AuthIntent.EnterPhoneNumber(phoneNumber))
        },
        onCountryCodeSubmit = { countryCode ->
            authViewModel.processIntent(AuthIntent.EnterCountryCode(countryCode))
        },
        onSendCodeClick = {
            authViewModel.processIntent(AuthIntent.SendAuthCode)
        },
        onCodeSubmit = { code, phoneNumber ->
            authViewModel.processIntent(AuthIntent.EnterVerificationCode(code, phoneNumber))
            authViewModel.processIntent(AuthIntent.VerifyAuthCode)
        },
        navController = navController
    )

}

@Composable
fun AuthorizationScreenContent(
    state: AuthState,
    onPhoneNumberSubmit: (String) -> Unit,
    onCountryCodeSubmit: (String) -> Unit,
    onSendCodeClick: () -> Unit,
    onCodeSubmit: (String, String) -> Unit,
    navController: NavHostController
) {
    var phoneNumber by remember { mutableStateOf(state.phoneNumber) }
    var verificationCode by remember { mutableStateOf(state.verificationCode) }
    val context = LocalContext.current

    val invalidProneNumberError = stringResource(id = R.string.invalid_phone_number)
    val noPhoneNumberError = stringResource(id = R.string.please_enter_phone_number)

    val isPhoneNumberValid = remember(phoneNumber, state.countryCode) {
        val digitCount = phoneNumber.filter { it.isDigit() }.length
        when (state.countryCode) {
            "+7", "+1" -> digitCount == 10
            "+375" -> digitCount == 9
            else -> false
        }
    }

    Surface(
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.large),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!state.isCodeSent) {
                PhoneNumberInputField(
                    phoneNumber = phoneNumber,
                    onPhoneNumberChange = {
                        phoneNumber = it
                        onPhoneNumberSubmit(it)
                    },
                    currentRegion = state.countryCode,
                    onCountryCodeChange = { code ->
                        onCountryCodeSubmit(code)
                    }
                )

                ButtonSign(
                    onclick = {
                        if (isPhoneNumberValid) {
                            onSendCodeClick()
                        } else {
                            Toast.makeText(context, invalidProneNumberError, Toast.LENGTH_SHORT).show()
                        }
                    },
                    signInOrSignUp = stringResource(id = R.string.send_code),
                    enabled = isPhoneNumberValid
                )
            } else {
                CodeInputField(
                    code = verificationCode,
                    onCodeChange = {
                        verificationCode = it
                    }
                )
                ButtonSign(
                    onclick = { onCodeSubmit(verificationCode, state.countryCode + phoneNumber) },
                    signInOrSignUp = stringResource(id = R.string.verify)
                )
            }

            if (state.errorMessage != null) {
                Text(
                    text = state.errorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            if (state.isUserExist == true) {
                LaunchedEffect(state.isUserExist) {
                    navController.navigate("chatList")
                }
            } else if (state.isUserExist == false) {
                LaunchedEffect(state.isUserExist) {
                    navController.navigate("signUp/${state.phoneNumber}")
                }
            }

            BottomRouteSign(
                onclick = {
                    if (isPhoneNumberValid){
                        navController.navigate("signUp/${state.countryCode}${state.phoneNumber}")
                    } else Toast.makeText(context, noPhoneNumberError, Toast.LENGTH_SHORT).show()
                },
                signInOrSignUp = stringResource(id = R.string.sign_up),
                label = stringResource(id = R.string.dont_have_account)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewDarkAuthorizationScreen() {
    ComposeMessangerTheme(darkTheme = true){
        AuthorizationScreenContent(
            state = AuthState(),
            onPhoneNumberSubmit = {},
            onCountryCodeSubmit = {},
            onSendCodeClick = {},
            onCodeSubmit = { _, _ -> },
            navController = rememberNavController()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAuthorizationScreen() {
    AuthorizationScreenContent(
        state = AuthState(),
        onPhoneNumberSubmit = {},
        onCountryCodeSubmit = {},
        onSendCodeClick = {},
        onCodeSubmit = { _, _ -> },
        navController = rememberNavController()
    )

}

