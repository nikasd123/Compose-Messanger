package com.kaz4.composemessanger.ui.chat_list

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kaz4.composemessanger.ui.chat_list.components.AcceptPendingRequestList
import com.kaz4.composemessanger.ui.profile.components.ProfileAppBar

@Composable
fun Userlist() {
    Column(
        modifier = Modifier
            .focusable()
    ) {
        ProfileAppBar(onClick = {

        })
        AcceptPendingRequestList(onclick = {

        })
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserList() {
    Userlist()
}