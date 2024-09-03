package com.kaz4.composemessanger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeMessangerTheme {
                val navController = rememberNavController()
                AppNavHost(navController)


            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "signIn") {
        composable("signIn") { AuthorizationScreen(navController = navController) }
        composable("signUp") { RegistrationScreen(navController = navController) }
        composable("chatList") { UserList(navController = navController) }
        composable("chat/{registerUUID}") { backStackEntry ->
            val registerUUID = backStackEntry.arguments?.getString("registerUUID") ?: ""
            ChatScreen(navController = navController, registerUUID = registerUUID)
        }
        composable("profile") { ProfileScreen(navController = navController) }
    }
}

