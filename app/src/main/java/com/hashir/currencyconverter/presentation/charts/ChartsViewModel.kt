package com.hashir.currencyconverter.presentation.charts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hashir.currencyconverter.data.NetworkResult
import com.hashir.currencyconverter.data.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChartsViewModel @Inject constructor(private val userRepositoryImpl: UserRepositoryImpl) :
    ViewModel() {

    private var _state = MutableStateFlow<ChartScreenState>(ChartScreenState.Idle)
    val state = _state.asStateFlow()
    var supportedCodes = ArrayList<String>()

    private var _chartScreenData = MutableStateFlow(ChartScreenUIState()) //Default is USD
    val chartScreenData = _chartScreenData.asStateFlow()

    init {
        getSupportedCodes()
    }

    fun getChartData() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.emit(ChartScreenState.Loading)
            val currentState = _chartScreenData.value

            val api = userRepositoryImpl.getChartData(
                currencyToExchange = currentState.selectedCurrency,
                timeSpan = currentState.timePeriod.timeSpan(),
                fromDate = currentState.timePeriod.dateRange().first,
                toDate = currentState.timePeriod.dateRange().second,
            )
            when (api) {
                is NetworkResult.Error -> {
                    _state.emit(ChartScreenState.Error(message = api.message ?: ""))
                }

                is NetworkResult.Loading -> {
                    _state.emit(ChartScreenState.Loading)
                }

                is NetworkResult.Success -> {
                    if (api.data?.results == null) {
                        _state.emit(ChartScreenState.Error(message = "No records found"))
                    } else {
                        _state.emit(ChartScreenState.ChartDataAPISuccess(api.data.results))
                    }

                }
            }
        }
    }


    private fun getSupportedCodes() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.emit(ChartScreenState.Loading)
            val api = userRepositoryImpl.getSupportedCode()
            //  withContext(Dispatchers.Main) {
            when (api) {
                is NetworkResult.Error -> {
                    _state.emit(ChartScreenState.Error(message = api.message ?: ""))
                }

                is NetworkResult.Loading -> {
                    _state.emit(ChartScreenState.Loading)
                }

                is NetworkResult.Success -> {
                    withContext(Dispatchers.Default) {
                        api.data?.supportedCodes?.let { outerList ->
                            outerList.forEach {
                                supportedCodes.add(it?.get(0) ?: "")
                            }
                        }
                        _state.emit(ChartScreenState.SupportedCodesAPISuccess(supportedCodes))
                    }
                }
            }
            // }
        }
    }

    fun updateInputValue(value: Double?) {
        viewModelScope.launch {
            value?.let {
                _chartScreenData.emit(_chartScreenData.value.copy(currencyValue = it))
            }
        }
    }

    fun updateSelectedCurrency(string: String) {
        viewModelScope.launch {
            _chartScreenData.emit(_chartScreenData.value.copy(selectedCurrency = string))
        }
    }

    fun updateSelectedTimePeriod(timePeriod: TimePeriod) {
        viewModelScope.launch {
            _chartScreenData.emit(_chartScreenData.value.copy(timePeriod = timePeriod))
        }
    }

    //fun update

    sealed class ChartScreenState {
        object Loading : ChartScreenState()
        data class SupportedCodesAPISuccess(val listOfCurrencies: List<String>) : ChartScreenState()
        data class ChartDataAPISuccess(val list: List<ChartsResponseModel.Result?>) :
            ChartScreenState()

        data class Error(val message: String) : ChartScreenState()
        object Idle : ChartScreenState()
    }
}