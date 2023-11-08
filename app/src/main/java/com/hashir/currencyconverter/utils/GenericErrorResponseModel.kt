package com.hashir.currencyconverter.utils

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class GenericErrorResponseModel(
    @SerializedName("result") val status: String?,  @SerializedName("error-type") val message: String?
)

@Keep
@JsonClass(generateAdapter = true)
data class GenericErrorResponseModelForPolygonApi(
    @SerializedName("error") val message: String?, @SerializedName("status") val status: String?
)
