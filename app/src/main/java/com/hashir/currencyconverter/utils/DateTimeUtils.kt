package com.hashir.currencyconverter.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.Calendar
import java.util.Locale

fun getDateRangeForOneDay(): Pair<String, String> {
    val dayBeforePreviousDay = LocalDate.now().minusDays(2)
    val previousDay = LocalDate.now().minusDays(1)
    val a = dayBeforePreviousDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    val b = previousDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    return Pair(a, b)
}

fun getLastMonthRange(): Pair<String, String> {
    val currentMonthFirstDay = LocalDate.now().withDayOfMonth(1)
    val previousMonthLastDay =
        LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).minusMonths(1)

    val formattedFirstDay = currentMonthFirstDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    val formattedLastDay = previousMonthLastDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    return Pair(formattedLastDay, formattedFirstDay)
}

fun getDateRangeForThisYear(): Pair<String, String> {
    val thisYearFirstDay = LocalDate.now().withDayOfYear(1)
    val formattedDate = thisYearFirstDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    val previousDay = LocalDate.now().minusDays(1)
    val latest = previousDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    return Pair(formattedDate, latest)
}

fun convertMillisToFormattedDate(millis: Long): String {
    val dateFormat = SimpleDateFormat("YYYY-MM-DD", Locale.getDefault())
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = millis
    return dateFormat.format(calendar.time)
}