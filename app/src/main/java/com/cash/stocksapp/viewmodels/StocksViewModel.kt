package com.cash.stocksapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cash.stocksapp.models.StockData
import com.cash.stocksapp.usecases.FetchStocksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StocksViewModel @Inject constructor(
    private val fetchStocksUseCase: FetchStocksUseCase
) : ViewModel() {

    private var getStocksDataJob: Job? = null

    private var _stocksFeedUiState: MutableStateFlow<StocksFeedUiState> = MutableStateFlow(
        StocksFeedUiState(isLoading = true)
    )
    val stocksFeedUiState: StateFlow<StocksFeedUiState> = _stocksFeedUiState

    init {
        fetchStocksFeed()
    }

    private fun fetchStocksFeed() {
        if (getStocksDataJob?.isActive == true) {
            return
        }

        getStocksDataJob = viewModelScope.launch {
            //delay(3000)
            val result = fetchStocksUseCase.invoke()
            if (result.isSuccess) {
                result.getOrThrow().apply {
                    _stocksFeedUiState.update { currentUiState ->
                        currentUiState.copy(
                            stocks = this.stocks,
                            isLoading = false
                        )
                    }
                }
            } else {
                result.exceptionOrNull().apply {
                    _stocksFeedUiState.update { currentUiState ->
                        currentUiState.copy(
                            failureMessage = this?.message,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun refreshStocksFeed() {
        // first reset the StateFlow before re-fetching
        _stocksFeedUiState.update { currentUiState ->
            currentUiState.copy(
                stocks = emptyList(),
                isLoading = true,
                failureMessage = null
            )
        }
        fetchStocksFeed()
    }

    fun cancelFetch() {
        getStocksDataJob?.cancel()
    }
}

data class StocksFeedUiState(
    val stocks: List<StockData> = emptyList(),
    val isLoading: Boolean = false,
    val failureMessage: String? = null
)
