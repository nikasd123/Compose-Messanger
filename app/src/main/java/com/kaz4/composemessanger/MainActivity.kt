package com.kaz4.composemessanger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.kaz4.composemessanger.ui.auth.screens.RegistrationScreen
import com.kaz4.composemessanger.ui.theme.ComposeMessangerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeMessangerTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
//                    AuthorizationScreen(
//                        onPhoneNumberSubmit = { phoneNumber ->
//                            // Логика отправки номера телефона
//                            // Например, отправка запроса на сервер
//                        },
//                        onCountryCodeSubmit = { countryCode ->
//                            // Логика отправки номера телефона
//                            // Например, отправка запроса на сервер
//                        },
//                        onCodeSubmit = { code ->
//                            // Логика отправки кода подтверждения
//                            // Например, отправка запроса на сервер
//                        },
//                        modifier = Modifier.padding(innerPadding)
//                    )
                    RegistrationScreen(
                        phoneNumber = "+7 (999) 999-99-99",
                        onUsernameChange = {
                            
                        },
                        onRegister = { phoneNumber, username ->

                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
