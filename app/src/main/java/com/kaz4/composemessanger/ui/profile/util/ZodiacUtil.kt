package com.kaz4.composemessanger.ui.profile.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun getZodiacSign(birthDate: String?): String {
    if (birthDate.isNullOrEmpty()) {
        return "Неизвестно"
    }

    val formatters = listOf(
        DateTimeFormatter.ofPattern("yyyy-MM-dd"),
        DateTimeFormatter.ofPattern("yyyy.MM.dd"),
        DateTimeFormatter.ofPattern("dd.MM.yyyy"),
        DateTimeFormatter.ofPattern("dd-MM-yyyy")
    )

    val date = formatters.asSequence().mapNotNull { formatter ->
        try {
            LocalDate.parse(birthDate, formatter)
        } catch (e: DateTimeParseException) {
            null
        }
    }.firstOrNull()

    return if (date != null) {
        val month = date.monthValue
        val day = date.dayOfMonth

        when (month) {
            1 -> if (day >= 20) "Водолей" else "Козерог"
            2 -> if (day <= 18) "Водолей" else "Рыбы"
            3 -> if (day <= 20) "Рыбы" else "Овен"
            4 -> if (day <= 19) "Овен" else "Телец"
            5 -> if (day <= 20) "Телец" else "Близнецы"
            6 -> if (day <= 20) "Близнецы" else "Рак"
            7 -> if (day <= 22) "Рак" else "Лев"
            8 -> if (day <= 22) "Лев" else "Дева"
            9 -> if (day <= 22) "Дева" else "Весы"
            10 -> if (day <= 22) "Весы" else "Скорпион"
            11 -> if (day <= 21) "Скорпион" else "Стрелец"
            12 -> if (day <= 21) "Стрелец" else "Козерог"
            else -> "Неизвестно"
        }
    } else {
        "Неизвестно"
    }
}