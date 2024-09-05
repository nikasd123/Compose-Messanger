package com.kaz4.composemessanger.ui.chat_list

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kaz4.composemessanger.ui.chat_list.components.FriendList
import com.kaz4.composemessanger.ui.profile.components.ProfileAppBar

@Composable
fun UserList(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    Surface(onClick = {  }) {
        Column(
            modifier = Modifier
                .focusable()
        ) {
            ProfileAppBar(onClick = {
                navController.navigate("profile")
            })
            FriendList(onclick = { registerUUID ->
                navController.navigate("chat/$registerUUID")
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserList() {
    UserList()
}