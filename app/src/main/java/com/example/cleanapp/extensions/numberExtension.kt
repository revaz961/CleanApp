package com.example.cleanapp.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

fun Long.toDateFormat(pattern: String): String {
    val date = Date(this)
    val format = SimpleDateFormat(pattern)
    return format.format(date)
}

fun Int.minuteToHourString() = "${this / 60} hours : ${this % 60} minutes"

fun Int.minuteToHoursFloat() = this / 60f

fun Float.roundToDecimal() = (this * 10).roundToInt() / 10f