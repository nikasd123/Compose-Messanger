package com.kaz4.composemessanger.ui.profile.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun birthDateVisualTransformation(): VisualTransformation =
    VisualTransformation { text ->
        val trimmed = if (text.text.length >= 8) {
            text.text.substring(0, 8)
        } else {
            text.text
        }

        val formatted = buildString {
            append(trimmed.take(2))
            if (trimmed.length > 2) append("-")
            append(trimmed.drop(2).take(2))
            if (trimmed.length > 4) append("-")
            append(trimmed.drop(4))
        }

        val originalToTransformed = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int =
                when {
                    offset <= 2 -> offset
                    offset <= 4 -> offset + 1
                    offset <= 8 -> offset + 2
                    else -> formatted.length
                }

            override fun transformedToOriginal(offset: Int): Int =
                when {
                    offset <= 2 -> offset
                    offset <= 5 -> offset - 1
                    offset <= 10 -> offset - 2
                    else -> text.text.length
                }
        }

        TransformedText(
            text = AnnotatedString(formatted),
            offsetMapping = originalToTransformed
        )
    }


fun formatDateForServer(date: String): String? {
    return try {
        if (date.isEmpty()) {
            return null
        }

        val formatters = listOf(
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("yyyy.MM.dd"),
            DateTimeFormatter.ofPattern("dd.MM.yyyy"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy")
        )

        val localDate = formatters.asSequence().mapNotNull { formatter ->
            try {
                LocalDate.parse(date, formatter)
            } catch (e: DateTimeParseException) {
                null
            }
        }.firstOrNull()

        return localDate?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    } catch (e: Exception) {
        null
    }
}