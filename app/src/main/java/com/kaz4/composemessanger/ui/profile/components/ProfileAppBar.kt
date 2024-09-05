package com.kaz4.composemessanger.ui.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaz4.composemessanger.ui.theme.ComposeMessangerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileAppBar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    imageVector = Icons.Filled.AccountBox,
                    contentDescription = null,
                    modifier = Modifier
                        .size(65.dp)
                        .clickable { onClick() }
                )
                Text(text = "Profile")
            }
        },
        modifier = modifier.statusBarsPadding()
    )
}

@Preview
@Composable
fun PreviewProfileAppBar() {
    ProfileAppBar {}
}

@Preview()
@Composable
fun PreviewDarkProfileAppBar() {
    ComposeMessangerTheme(darkTheme = true) {
        ProfileAppBar {}
    }
}