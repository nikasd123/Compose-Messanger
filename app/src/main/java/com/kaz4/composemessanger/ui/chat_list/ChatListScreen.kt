package com.kaz4.composemessanger.ui.chat_list

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kaz4.composemessanger.ui.chat_list.components.FriendList
import com.kaz4.composemessanger.ui.profile.components.ProfileAppBar
import com.kaz4.composemessanger.ui.theme.ComposeMessangerTheme

@Composable
fun UserList(
    navController: NavHostController = rememberNavController()
) {
    val backgroundColor = if (isSystemInDarkTheme()) {
        MaterialTheme.colorScheme.background
    } else {
        MaterialTheme.colorScheme.background
    }

    Column(
        modifier = Modifier
            .focusable()
            .background(backgroundColor)
    ) {
        ProfileAppBar(onClick = {
            navController.navigate("profile")
        })
        FriendList(onclick = { registerUUID ->
            navController.navigate("chat/$registerUUID")
        })
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewUserList() {
    UserList()
}

@Preview(showBackground = true)
@Composable
fun PreviewDarkUserList() {
    ComposeMessangerTheme(darkTheme = true) {
        UserList()
    }
}