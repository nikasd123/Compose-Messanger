package com.kaz4.composemessanger.ui.auth.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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

@Composable
fun CountryCodePicker(
    selectedCountryCode: String,
    onCountryCodeChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Row(
            modifier = Modifier
                .clickable { expanded = true }
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedCountryCode,
                style = MaterialTheme.typography.titleMedium
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    onCountryCodeChange("+1")
                    expanded = false
                },
                text = { Text(text = "+1 USA") }
            )
            DropdownMenuItem(
                onClick = {
                    onCountryCodeChange("+7")
                    expanded = false
                },
                text = { Text(text = "+7 Russia") }
            )
            DropdownMenuItem(
                onClick = {
                    onCountryCodeChange("+44")
                    expanded = false
                },
                text = { Text(text = "+44 UK") }
            )
        }
    }
}

fun extractCountryCode(phoneNumber: String): String =
    when {
        phoneNumber.startsWith("+1") -> "+1"
        phoneNumber.startsWith("+7") -> "+7"
        phoneNumber.startsWith("+44") -> "+44"
        else -> "+7"
    }

fun phoneNumberVisualTransformation(): VisualTransformation =
    VisualTransformation { text ->
        // Определяем форматирование
        val trimmed = if (text.text.length >= 10) {
            text.text.substring(0, 10)
        } else {
            text.text
        }

        val formatted = buildString {
            append(trimmed.take(3))
            if (trimmed.length > 3) append("-")
            append(trimmed.drop(3).take(3))
            if (trimmed.length > 6) append("-")
            append(trimmed.drop(6))
        }

        val originalToTransformed = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int =
                when {
                    offset <= 3 -> offset
                    offset <= 6 -> offset + 1
                    offset <= 10 -> offset + 2
                    else -> formatted.length
                }

            override fun transformedToOriginal(offset: Int): Int =
                when {
                    offset <= 3 -> offset
                    offset <= 7 -> offset - 1
                    offset <= 12 -> offset - 2
                    else -> text.text.length
                }
        }

        TransformedText(
            text = AnnotatedString(formatted),
            offsetMapping = originalToTransformed
        )
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