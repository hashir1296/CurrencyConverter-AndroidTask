package com.hashir.currencyconverter.presentation.rates


data class CurrencyDomainModel(
    val name: String,
    val rate: Double? = 0.0,
    val baseCurrencyToTarget: String,
    val targetCurrencyToBase: String
)