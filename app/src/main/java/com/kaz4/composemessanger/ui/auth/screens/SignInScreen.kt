package com.kaz4.composemessanger.ui.auth.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
import com.kaz4.composemessanger.ui.auth.components.CodeInputField
import com.kaz4.composemessanger.ui.auth.components.PhoneNumberInputField
import com.kaz4.composemessanger.ui.theme.spacing

@Composable
fun AuthorizationScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    AuthorizationScreenContent(
        onPhoneNumberSubmit = { phoneNumber ->
            Log.d("AAA", "phone: $phoneNumber")
        },
        onCountryCodeSubmit = { countryCode ->
            Log.d("AAA", "code: $countryCode")
        },
        onCodeSubmit = { code ->
            Log.d("AAA", "code: $code")
            val isUserExist = true
            if (isUserExist) {
                navController.navigate("chatList")
            } else { navController.navigate("signUp") }
        },
        modifier = modifier,
        navController = navController
    )
}

@Composable
fun AuthorizationScreenContent(
    onPhoneNumberSubmit: (String) -> Unit,
    onCountryCodeSubmit: (String) -> Unit,
    onCodeSubmit: (String) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    var phoneNumber by remember { mutableStateOf("") }
    var fullPhoneNumber by remember { mutableStateOf("") }
    var verificationCode by remember { mutableStateOf("") }
    var isCodeSent by remember { mutableStateOf(false) }
    var selectedCountryCode by remember { mutableStateOf("+7") }

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.large),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!isCodeSent) {
            PhoneNumberInputField(
                phoneNumber = phoneNumber,
                onPhoneNumberChange = { phoneNumber = it },
                currentRegion = selectedCountryCode,
                onCountryCodeChange = { newCode ->
                    selectedCountryCode = newCode
                }
            )
            ButtonSign(
                onclick = {
                    fullPhoneNumber = selectedCountryCode + phoneNumber
                    onCountryCodeSubmit(selectedCountryCode)
                    onPhoneNumberSubmit(phoneNumber)
                    if (fullPhoneNumber.length == 12) {
                        isCodeSent = true
                    } else {
                        Toast.makeText(context, "Invalid phone number", Toast.LENGTH_SHORT).show()
                    }
                    Log.d("AAA", "phone: $fullPhoneNumber")
                },
                signInOrSignUp = "Send Code"
            )
        } else {
            CodeInputField(
                code = verificationCode,
                onCodeChange = { verificationCode = it }
            )
            ButtonSign(
                onclick = {
                    onPhoneNumberSubmit(fullPhoneNumber)
                    onCodeSubmit(verificationCode)
                },
                signInOrSignUp = "Verify"
            )
        }

        BottomRouteSign(
            onclick = {
                //todo nav to registration
                navController.navigate("signUp")
            },
            signInOrSignUp = "Sign Up",
            label = "Don't have an account?"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAuthorizationScreen() {
    AuthorizationScreenContent(
        onPhoneNumberSubmit = {},
        onCountryCodeSubmit = {},
        onCodeSubmit = {},
        navController = rememberNavController()
    )
}
