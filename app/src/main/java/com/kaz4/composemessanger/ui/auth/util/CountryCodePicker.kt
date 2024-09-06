package com.kaz4.composemessanger.ui.auth.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kaz4.composemessanger.R

@Composable
fun CountryCodePicker(
    selectedCountryCode: String,
    onCountryCodeChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val countryFlagMap = mapOf(
        "+1" to R.drawable.ic_flag_us,
        "+7" to R.drawable.ic_flag_ru,
        "+375" to R.drawable.ic_flag_by
    )

    Box {
        Row(
            modifier = Modifier
                .clickable { expanded = true }
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val flagRes = countryFlagMap[selectedCountryCode] ?: R.drawable.ic_flag_ru
            Image(
                modifier = Modifier
                    .requiredHeight(20.dp)
                    .requiredWidth(30.dp),
                painter = painterResource(id = flagRes),
                contentDescription = stringResource(id = R.string.flag),
                contentScale = ContentScale.Crop
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
                text = { Text(text = stringResource(id = R.string.country_usa)) },
                leadingIcon = {
                    Image(
                        modifier = Modifier
                            .requiredHeight(10.dp)
                            .requiredWidth(15.dp),
                        painter = painterResource(id = R.drawable.ic_flag_us),
                        contentDescription = stringResource(id = R.string.flag_description_us),
                        contentScale = ContentScale.Crop
                    )
                }
            )
            DropdownMenuItem(
                onClick = {
                    onCountryCodeChange("+7")
                    expanded = false
                },
                text = { Text(text = stringResource(id = R.string.country_russia)) },
                leadingIcon = {
                    Image(
                        modifier = Modifier
                            .requiredHeight(10.dp)
                            .requiredWidth(15.dp),
                        painter = painterResource(id = R.drawable.ic_flag_ru),
                        contentDescription = stringResource(id = R.string.flag_description_russia),
                        contentScale = ContentScale.Crop
                    )
                }
            )
            DropdownMenuItem(
                onClick = {
                    onCountryCodeChange("+375")
                    expanded = false
                },
                text = { Text(text = stringResource(id = R.string.country_belarus)) },
                leadingIcon = {
                    Image(
                        modifier = Modifier
                            .requiredHeight(10.dp)
                            .requiredWidth(15.dp),
                        painter = painterResource(id = R.drawable.ic_flag_by),
                        contentDescription = stringResource(id = R.string.flag_description_belarus),
                        contentScale = ContentScale.Crop
                    )
                }
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