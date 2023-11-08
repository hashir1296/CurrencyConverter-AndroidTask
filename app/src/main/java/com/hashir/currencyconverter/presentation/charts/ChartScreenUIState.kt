package com.hashir.currencyconverter.presentation.charts

data class ChartScreenUIState(
    val timePeriod: TimePeriod = TimePeriod.ONE_DAY,
    val selectedCurrency: String = "USD",
    var currencyValue: Double = 1.0
)
