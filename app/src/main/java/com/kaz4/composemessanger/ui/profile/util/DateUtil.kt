package com.kaz4.composemessanger.ui.profile.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun formatDateInputWithCursor(input: String, initialCursorPosition: Int): Pair<String, Int> {
    val digitsOnly = input.replace(Regex("[^0-9]"), "")

    val formatted = StringBuilder()
    var cursorPosition = initialCursorPosition
    var numOfDots = 0

    for (i in digitsOnly.indices) {
        if (i == 2 || i == 4) {
            formatted.append('.')
            numOfDots++
            if (i < initialCursorPosition) {
                cursorPosition++
            }
        }
        formatted.append(digitsOnly[i])
    }

    if (cursorPosition > formatted.length) {
        cursorPosition = formatted.length
    }

    return formatted.toString() to cursorPosition
}

class DateVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val formattedText = formatDateInput(text.text)
        return TransformedText(
            text = AnnotatedString(formattedText),
            offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int = offset
                override fun transformedToOriginal(offset: Int): Int = offset
            }
        )
    }
}

fun formatDateInput(input: String): String {
    val cleanedInput = input.replace(Regex("[^0-9]"), "")
    val sb = StringBuilder()

    for (i in cleanedInput.indices) {
        if (i == 2 || i == 4) {
            sb.append('.')
        }
        sb.append(cleanedInput[i])
    }

    return sb.toString()
}

fun formatDateForServer(date: String): String? {
    return try {
        if (date.isNullOrEmpty()) {
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