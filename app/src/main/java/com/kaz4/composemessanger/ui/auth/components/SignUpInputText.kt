package com.kaz4.composemessanger.ui.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun UsernameTextField(
    entry: String,
    hint: String,
    onChange: (String) -> Unit = {},
    readOnly: Boolean = false
) {
    var text by remember { mutableStateOf(entry) }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        label = { Text(text = hint) },
        onValueChange = { newText ->
            val validCharacters = Regex("^[A-Za-z0-9-_]*$")
            if (validCharacters.matches(newText)) {
                text = newText
                onChange(newText)
            }
        },
        readOnly = readOnly,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null
            )
        }
    )
}



@Composable
fun PasswordTextField(
    entry: String,
    hint: String,
    onChange: (String) -> Unit = {}
) {
    var text by remember { mutableStateOf("") }
    text = entry
    var passwordVisibility by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        label = { Text(hint) },
        onValueChange = {
            text = it
            onChange(it)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null
            )
        },
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisibility)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            IconButton(onClick = {
                passwordVisibility = !passwordVisibility
            }) {
                Icon(imageVector = image, "")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginTextField() {
    UsernameTextField(
        entry = "",
        hint = "Enter your username",
        onChange = {  }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewPasswordTextField() {
    PasswordTextField(
        entry = "password",
        hint = "Password",
        onChange = {  }
    )
}