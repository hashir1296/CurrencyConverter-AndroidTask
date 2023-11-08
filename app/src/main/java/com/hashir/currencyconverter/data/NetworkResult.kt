package com.hashir.currencyconverter.data

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T?) : NetworkResult<T>()
    data class Error<T>(
        val message: String?,
        val code: String? = null,
        val status: String? = null
    ) : NetworkResult<T>()

    class Loading<T> : NetworkResult<T>()
}