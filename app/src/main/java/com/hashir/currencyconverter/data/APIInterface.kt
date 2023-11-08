package com.hashir.currencyconverter.data

import com.hashir.currencyconverter.BuildConfig
import com.hashir.currencyconverter.presentation.charts.ChartsResponseModel
import com.hashir.currencyconverter.presentation.charts.SupportedCodesResponseModel
import com.hashir.currencyconverter.presentation.rates.CurrencyRateResponseModel
import com.hashir.currencyconverter.utils.Constants
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIInterface {

    @GET(Constants.EndPoints.LATEST_RATE)
    fun getCurrentRates(): Deferred<Response<CurrencyRateResponseModel>>

    @GET(Constants.EndPoints.SUPPORTED_CODE)
    fun getSupportedCodes(): Deferred<Response<SupportedCodesResponseModel>>

    @GET("https://api.polygon.io/v2/aggs/ticker/C:EUR{forexTicker}/range/{multiplier}/{timespan}/{from}/{to}")
    fun getChartData(
        @Path("forexTicker") currencyToExchange: String,
        @Path("multiplier") multiplier: Int = 1,
        @Path("timespan") timeSpan: String,
        @Path("from") from: String,
        @Path("to") to: String,
        @Query("adjusted") adjusted: Boolean = true,
        @Query("limit") limit: Int = 120,
        @Query("sort") sort: String = "asc",
        @Query("apiKey") apiKey: String = BuildConfig.CHARTS_API_KEY
    ): Deferred<Response<ChartsResponseModel>>

}