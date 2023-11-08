package com.hashir.currencyconverter.data

import com.hashir.currencyconverter.utils.Constants
import okhttp3.Interceptor

fun headersInterceptor() = Interceptor { chain ->

    chain.proceed(
        chain.request().newBuilder().apply {
            addHeader(
                "accept", "application/json"
            )
        }.build()
    )
}