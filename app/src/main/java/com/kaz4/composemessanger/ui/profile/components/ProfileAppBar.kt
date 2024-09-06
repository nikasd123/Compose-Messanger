package com.kaz4.composemessanger.ui.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaz4.composemessanger.R
import com.kaz4.composemessanger.ui.theme.ComposeMessangerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileAppBar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSystemInDarkTheme()) {
        MaterialTheme.colorScheme.outlineVariant
    } else {
        MaterialTheme.colorScheme.outlineVariant
    }

    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Profile Icon",
                    modifier = Modifier
                        .size(48.dp)
                        .clickable { onClick() }
                )
                Text(
                    text = stringResource(id = R.string.profile),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
        modifier = modifier
            .statusBarsPadding()
            .clip(shape = RoundedCornerShape(bottomStart = 30.dp, topEnd = 30.dp)),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor
        )
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