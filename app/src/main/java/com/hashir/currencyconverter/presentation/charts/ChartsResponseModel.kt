package com.hashir.currencyconverter.presentation.charts


import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class ChartsResponseModel(
    @Json(name = "adjusted") val adjusted: Boolean?,
    @Json(name = "queryCount") val queryCount: Int?,
    @Json(name = "request_id") val requestId: String?,
    @Json(name = "results") val results: List<Result?>?,
    @Json(name = "resultsCount") val resultsCount: Int?,
    @Json(name = "status") val status: String?,
    @Json(name = "ticker") val ticker: String?
) {
    @Keep
    @JsonClass(generateAdapter = true)
    data class Result(
        @Json(name = "t") val t: Long?,
        @Json(name = "c") val c: Double?
        /*@Json(name = "h") val h: Double?,
        @Json(name = "l") val l: Double?,
        @Json(name = "o") val o: Double?,*/
    )
}