package com.hashir.currencyconverter.presentation.charts

import com.hashir.currencyconverter.utils.getDateRangeForOneDay
import com.hashir.currencyconverter.utils.getDateRangeForThisYear
import com.hashir.currencyconverter.utils.getLastMonthRange

enum class TimePeriod {
    ONE_DAY, ONE_MONTH, ONE_YEAR
}

fun TimePeriod.dateRange(): Pair<String, String> {
    return when(this){
        TimePeriod.ONE_DAY -> {
           getDateRangeForOneDay()
        }
        TimePeriod.ONE_MONTH -> {
            getLastMonthRange()
        }
        TimePeriod.ONE_YEAR -> {
            getDateRangeForThisYear()
        }
    }
}

fun TimePeriod.timeSpan(): String {
    return when (this) {
        TimePeriod.ONE_DAY -> "hour"
        TimePeriod.ONE_MONTH -> "month"
        TimePeriod.ONE_YEAR -> "year"
    }
}
