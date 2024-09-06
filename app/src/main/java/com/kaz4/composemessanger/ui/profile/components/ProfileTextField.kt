package com.kaz4.composemessanger.ui.profile.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.kaz4.composemessanger.R
import com.kaz4.composemessanger.ui.theme.spacing

@Composable
fun ProfileTextField(
    modifier: Modifier = Modifier,
    entry: String,
    hint: String,
    onChange: (String) -> Unit = {},
    readOnly: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    var isNameChange by remember { mutableStateOf(false) }
    var isFocusChange by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(entry) }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = MaterialTheme.spacing.medium),
        label = { Text(text = hint) },
        value = text,
        onValueChange = {
            text = it
            onChange(it)
            isNameChange = true
        },
        readOnly = readOnly,
        visualTransformation = visualTransformation,
        singleLine = isSingleLine(hint),
        maxLines = initProfileMaxLines(hint),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

@Composable
private fun isSingleLine(hintType: String): Boolean =
    hintType != stringResource(id = R.string.hint_about_you)

@Composable
private fun initProfileMaxLines(hintType: String): Int =
    if (hintType != stringResource(id = R.string.hint_about_you)) 1 else 5

@Preview(showBackground = true)
@Composable
fun PreviewProfileTextField() {
    ProfileTextField(
        entry = "John Doe",
        hint = "Enter your name",
        onChange = { newText -> println("Text changed: $newText") },
        keyboardType = KeyboardType.Text
    )
}
