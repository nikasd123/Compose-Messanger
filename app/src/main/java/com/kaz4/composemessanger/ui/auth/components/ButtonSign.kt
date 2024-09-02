package com.kaz4.composemessanger.ui.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kaz4.composemessanger.ui.theme.spacing

@Composable
fun ButtonSign(
    onclick: () -> Unit,
    signInOrSignUp: String
) {
    Button(
        modifier = Modifier
            .padding(top = MaterialTheme.spacing.large)
            .fillMaxWidth(),
        onClick = { onclick() }
    )
    {
        Text(
            text = signInOrSignUp,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewButtonSign() {
    ButtonSign(
        onclick = {  },
        signInOrSignUp = "Sign In"
    )
}