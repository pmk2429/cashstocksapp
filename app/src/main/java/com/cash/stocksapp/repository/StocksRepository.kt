package com.cash.stocksapp.repository

import com.cash.stocksapp.models.StocksResponse
import com.cash.stocksapp.service.StocksApiService
import com.cash.stocksapp.utils.CoroutineDispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StocksRepository @Inject constructor(
    private val stocksApiService: StocksApiService,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) {

    /**
     * Fetch relevant Stocks data from the backend API service using [StocksApiService].
     */
    suspend fun fetchStocksData(): Result<StocksResponse> =
        withContext(coroutineDispatcherProvider.io()) {
            return@withContext try {
                Result.success(stocksApiService.getStocksData())
            } catch (e: Exception) {
                handleError(e)
            }
        }

    suspend fun fetchMalformedStocksData(): Result<StocksResponse> =
        withContext(coroutineDispatcherProvider.io()) {
            try {
                val result = stocksApiService.getMalformedStocksData()
                Result.success(result)
            } catch (e: Exception) {
                handleError(e)
            }
        }

    suspend fun fetchEmptyStocksData(): Result<StocksResponse> =
        withContext(coroutineDispatcherProvider.io()) {
            return@withContext try {
                Result.success(stocksApiService.getEmptyStocksData())
            } catch (e: Exception) {
                handleError(e)
            }
        }

    private fun <AppError> handleError(t: Throwable): Result<AppError> {
        return Result.failure(AppError(message = "ERROR! : ${t.message}", cause = t.cause))
    }
}
