package com.hashir.currencyconverter.utils

import com.hashir.currencyconverter.BuildConfig

object Constants {
    const val CURRENCY_CODE = "EUR"
    const val API_KEY = BuildConfig.API_KEY
    const val BASE_URL = "https://v6.exchangerate-api.com/v6/$API_KEY/"

    object EndPoints {
        const val LATEST_RATE = "latest/$CURRENCY_CODE"
        const val SUPPORTED_CODE = "codes"
    }
}