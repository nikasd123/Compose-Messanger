package com.kaz4.composemessanger.ui.auth.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomRouteSign(
    modifier: Modifier = Modifier,
    onclick: () -> Unit,
    signInOrSignUp: String,
    label: String,
    enabled: Boolean = true
) {
    Surface(
        modifier = modifier
    )
    {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall
            )
            TextButton(
                enabled = enabled,
                onClick = onclick,
                content = {
                    Text(
                        text = signInOrSignUp,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomRouteSign() {
    BottomRouteSign(
        onclick = {  },
        signInOrSignUp = "Sign In",
        label = "Don't have an account?"
    )
}