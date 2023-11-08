package com.hashir.currencyconverter.data

import com.google.gson.Gson
import com.hashir.currencyconverter.presentation.charts.ChartsResponseModel
import com.hashir.currencyconverter.presentation.charts.SupportedCodesResponseModel
import com.hashir.currencyconverter.presentation.rates.CurrencyRateResponseModel
import com.hashir.currencyconverter.utils.GenericErrorResponseModel
import com.hashir.currencyconverter.utils.GenericErrorResponseModelForPolygonApi
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiInterface: APIInterface
) : UserRepository {
    override suspend fun getCurrencyRates(): NetworkResult<CurrencyRateResponseModel> {
        return try {
            val api = apiInterface.getCurrentRates().await()
            when {
                api.isSuccessful -> {
                    NetworkResult.Success(data = api.body())
                }

                else -> {
                    val errorBody = Gson().fromJson(api.errorBody()
                        ?.string(),
                        GenericErrorResponseModel::class.java)
                    NetworkResult.Error(
                        message = errorBody.message?:"Something went wrong", code = api.code().toString()
                    )
                }
            }
        } catch (ex: Exception) {
            NetworkResult.Error(
                message = ex.message
            )
        }
    }

    override suspend fun getSupportedCode(): NetworkResult<SupportedCodesResponseModel> {
        return try {
            val api = apiInterface.getSupportedCodes().await()
            when {
                api.isSuccessful -> {
                    NetworkResult.Success(data = api.body())
                }

                else -> {
                    val errorBody = Gson().fromJson(api.errorBody()
                        ?.string(),
                        GenericErrorResponseModel::class.java)
                    NetworkResult.Error(
                        message = errorBody.message?:"Something went wrong",status = errorBody.status, code = api.code().toString()
                    )
                }
            }
        } catch (ex: Exception) {
            NetworkResult.Error(
                message = ex.message
            )
        }
    }

    override suspend fun getChartData(
        currencyToExchange: String, timeSpan: String, fromDate: String, toDate: String
    ): NetworkResult<ChartsResponseModel> {
        return try {
            val api = apiInterface.getChartData(
                currencyToExchange = currencyToExchange,
                timeSpan = timeSpan,
                from = fromDate,
                to = toDate
            ).await()
            when {
                api.isSuccessful -> {
                    NetworkResult.Success(data = api.body())
                }

                else -> {
                    val errorBody = Gson().fromJson(api.errorBody()
                        ?.string(),
                        GenericErrorResponseModelForPolygonApi::class.java)
                    NetworkResult.Error(
                        message = errorBody.message?:"Something went wrong", status = errorBody.status, code = api.code().toString()
                    )
                }
            }
        } catch (ex: Exception) {
            NetworkResult.Error(
                message = ex.message
            )
        }
    }
}