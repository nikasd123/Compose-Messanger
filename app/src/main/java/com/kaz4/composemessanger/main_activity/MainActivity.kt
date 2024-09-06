package com.kaz4.composemessanger.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kaz4.composemessanger.ui.auth.screens.AuthorizationScreen
import com.kaz4.composemessanger.ui.auth.screens.RegistrationScreen
import com.kaz4.composemessanger.ui.chat.ChatScreen
import com.kaz4.composemessanger.ui.chat_list.UserList
import com.kaz4.composemessanger.ui.profile.ProfileScreen
import com.kaz4.composemessanger.ui.theme.ComposeMessangerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeMessangerTheme {
                val navController = rememberNavController()
                val uiState by mainViewModel.uiState.collectAsState()

                LaunchedEffect(uiState.isAuthenticated) {
                    if (uiState.isAuthenticated) {
                        navController.navigate("chatList") {
                            popUpTo("signIn") { inclusive = true }
                        }
                    } else {
                        navController.navigate("signIn") {
                            popUpTo("chatList") { inclusive = true }
                        }
                    }
                }

                AppNavHost(navController)
            }
        }
    }
}


@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "chatList") {
        composable("signIn") { AuthorizationScreen(navController = navController) }
        composable("signUp/{phoneNumber}") { backStackEntry ->
            val phoneNumber = backStackEntry.arguments?.getString("phoneNumber") ?: ""
            RegistrationScreen(navController = navController, phoneNumber = phoneNumber)
        }
        composable("chatList") { UserList(navController = navController) }
        composable("chat/{registerUUID}") { backStackEntry ->
            val registerUUID = backStackEntry.arguments?.getString("registerUUID") ?: ""
            ChatScreen(navController = navController, registerUUID = registerUUID)
        }
        composable("profile") { ProfileScreen(navController = navController) }
    }
}

