package com.hashir.currencyconverter.presentation.rates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hashir.currencyconverter.data.NetworkResult
import com.hashir.currencyconverter.data.UserRepositoryImpl
import com.hashir.currencyconverter.presentation.charts.ChartsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.reflect.full.memberProperties

@HiltViewModel
class RatesViewModel @Inject constructor(private val userRepository: UserRepositoryImpl) :
    ViewModel() {

    private var _state = MutableStateFlow<CurrencyScreenState>(CurrencyScreenState.Idle)
    val state = _state.asStateFlow()

    var inputAmount: Double = 1.0
        private set


    init {
        getCurrencyRates(inputAmount)
    }

    fun updateInputAmount(value: Double?) {
        value?.let {
            inputAmount = it
        }
        getCurrencyRates(inputAmount)
    }

    private fun getCurrencyRates(amount: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            if (amount == 0.0) {
                _state.emit(CurrencyScreenState.APISuccess(listOfCurrencies = emptyList()))
                return@launch
            }
            _state.emit(CurrencyScreenState.Loading)
            val api = userRepository.getCurrencyRates()
            withContext(Dispatchers.Main) {
                when (api) {
                    is NetworkResult.Error -> {
                        _state.emit(CurrencyScreenState.Error(message = api.message ?: ""))
                    }

                    is NetworkResult.Loading -> {
                        _state.emit(CurrencyScreenState.Loading)
                    }

                    is NetworkResult.Success -> {
                        api.data?.conversionRates?.let { currency ->
                            val listOfCurrencies = arrayListOf<CurrencyDomainModel>()
                            withContext(Dispatchers.Default) {
                                CurrencyRateResponseModel.ConversionRates::class.memberProperties.forEach { member ->
                                    val name = member.name.uppercase()
                                    val value = member.get(currency) as Double?
                                    val baseCurrencyToTarget = String.format("%.4f", value)
                                    val targetCurrencyToBase =
                                        String.format("%.4f", 1.0.div(value ?: 0.0))

                                    listOfCurrencies.add(
                                        CurrencyDomainModel(
                                            name = name,
                                            rate = amount?.times(value ?: 0.0),
                                            baseCurrencyToTarget = "1 EUR = $baseCurrencyToTarget $name",
                                            targetCurrencyToBase = "1 $name = $targetCurrencyToBase EUR"
                                        )
                                    )
                                }
                            }
                            _state.emit(CurrencyScreenState.APISuccess(listOfCurrencies = listOfCurrencies))
                        }
                    }
                }
            }
        }
    }

    sealed class CurrencyScreenState {
        object Loading : CurrencyScreenState()
        data class APISuccess(val listOfCurrencies: List<CurrencyDomainModel>) :
            CurrencyScreenState()

        data class Error(val message: String) : CurrencyScreenState()
        object Idle : CurrencyScreenState()
    }
}