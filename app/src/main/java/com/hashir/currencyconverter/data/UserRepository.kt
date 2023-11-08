package com.hashir.currencyconverter.data

import com.hashir.currencyconverter.presentation.charts.ChartsResponseModel
import com.hashir.currencyconverter.presentation.charts.SupportedCodesResponseModel
import com.hashir.currencyconverter.presentation.rates.CurrencyRateResponseModel

interface UserRepository {

    suspend fun getCurrencyRates(): NetworkResult<CurrencyRateResponseModel>

    suspend fun getSupportedCode(): NetworkResult<SupportedCodesResponseModel>

    suspend fun getChartData(
        currencyToExchange: String, timeSpan: String, fromDate: String, toDate: String
    ): NetworkResult<ChartsResponseModel>
}