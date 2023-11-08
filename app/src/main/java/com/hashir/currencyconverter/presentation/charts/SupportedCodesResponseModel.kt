package com.hashir.currencyconverter.presentation.charts


import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class SupportedCodesResponseModel(
    @Json(name = "documentation") val documentation: String?,
    @Json(name = "result") val result: String?,
    @Json(name = "supported_codes") val supportedCodes: List<List<String?>?>?,
    @Json(name = "terms_of_use") val termsOfUse: String?
)