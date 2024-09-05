package com.kaz4.composemessanger.ui.auth.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

fun phoneNumberVisualTransformation(): VisualTransformation =
    VisualTransformation { text ->
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