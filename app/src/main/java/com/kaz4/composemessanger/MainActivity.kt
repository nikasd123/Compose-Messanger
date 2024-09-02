package com.kaz4.composemessanger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.kaz4.composemessanger.ui.chat.ChatScreen
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
                    val keyboardController = LocalSoftwareKeyboardController.current

                    ChatScreen(
                        modifier = Modifier.padding(innerPadding),
                        keyboardController = keyboardController ?: error("Keyboard controller is null")
                    )
                }
            }
        }
    }
}
