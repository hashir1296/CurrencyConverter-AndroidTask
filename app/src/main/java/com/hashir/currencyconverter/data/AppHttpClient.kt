package com.hashir.currencyconverter.data

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.hashir.currencyconverter.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.time.Duration

object AppHttpClient {
    val safeOkHttpClient: OkHttpClient
        get() = try {
            val okHttpClientBuilder = OkHttpClient().newBuilder()

            //Only log apis when in debug mode
            if (BuildConfig.DEBUG) {
                okHttpClientBuilder.addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }

            okHttpClientBuilder.addInterceptor(headersInterceptor())
            okHttpClientBuilder.addInterceptor { chain ->
                val request = chain.request()
                val response = chain.proceed(request)
                response
            }.connectTimeout(Duration.ofSeconds(20)).readTimeout(Duration.ofSeconds(20))
                .callTimeout(Duration.ofSeconds(20)).writeTimeout(Duration.ofSeconds(20))
            okHttpClientBuilder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
}