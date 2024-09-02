package com.kaz4.composemessanger.ui.chat_list

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kaz4.composemessanger.ui.chat_list.components.FriendList
import com.kaz4.composemessanger.ui.profile.components.ProfileAppBar

@Composable
fun UserList() {
    Column(
        modifier = Modifier
            .focusable()
    ) {
        ProfileAppBar(onClick = {

        })
        FriendList(onclick = {

        })
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserList() {
    UserList()
}