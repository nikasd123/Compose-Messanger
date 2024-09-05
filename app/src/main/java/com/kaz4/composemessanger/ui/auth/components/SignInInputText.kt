package com.kaz4.composemessanger.ui.auth.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.kaz4.composemessanger.ui.auth.util.CountryCodePicker
import com.kaz4.composemessanger.ui.auth.util.phoneNumberVisualTransformation

@Composable
fun PhoneNumberInputField(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    currentRegion: String,
    placeholder: String = "Enter phone number",
    onCountryCodeChange: (String) -> Unit
) {
    var selectedCountryCode by remember { mutableStateOf(currentRegion) }
    var text by remember { mutableStateOf(phoneNumber) }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CountryCodePicker(
            selectedCountryCode = selectedCountryCode,
            onCountryCodeChange = { newCode ->
                selectedCountryCode = newCode
                onCountryCodeChange(newCode)
            }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            label = { Text(text = placeholder) },
            onValueChange = {
                if (it.length <= 10) {
                    text = it
                    onPhoneNumberChange(it)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            leadingIcon = {
                Text(
                    text = selectedCountryCode,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            visualTransformation = phoneNumberVisualTransformation()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPhoneNumberInputField() {
    PhoneNumberInputField(
        phoneNumber = "9219999999",
        onPhoneNumberChange = {},
        onCountryCodeChange = {},
        currentRegion = "+7"
    )
}