package com.kaz4.composemessanger.ui.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
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

@Composable
fun CodeInputField(
    code: String,
    onCodeChange: (String) -> Unit,
    placeholder: String = stringResource(id = R.string.enter_sms_code)
) {
    var text by remember { mutableStateOf(code) }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        label = { Text(text = placeholder) },
        onValueChange = {
            if (it.length <= 6 && it.all { char -> char.isDigit() }) {
                text = it
                onCodeChange(it)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        visualTransformation = VisualTransformation.None
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCodeInputField() {
    CodeInputField(
        code = "",
        onCodeChange = {}
    )
}
