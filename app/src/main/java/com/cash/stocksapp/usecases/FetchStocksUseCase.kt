package com.cash.stocksapp.usecases

import com.cash.stocksapp.models.StocksResponse
import com.cash.stocksapp.repository.StocksRepository
import com.cash.stocksapp.utils.CoroutineDispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

class FetchStocksUseCase @Inject constructor(
    private val stocksRepository: StocksRepository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) {

    /**
     * Randomly invokes the caller each time after being called.
     * The randomly selected number is then mapped to corresponding method in the Repository
     * in order to fetch the data from the backend hooked to different endpoints.
     * The idea for this method is to mimic the real life scenario of fetching different kind of
     * response on each call to backend server. That way, the multiple scenarios can be well tested.
     */
    suspend fun invoke(): Result<StocksResponse> =
        withContext(coroutineDispatcherProvider.io()) {
            val random = Random
            when (random.nextInt(4)) {
                1 -> stocksRepository.fetchStocksData()
                2 -> stocksRepository.fetchEmptyStocksData()
                3 -> stocksRepository.fetchMalformedStocksData()
                else -> stocksRepository.fetchStocksData()
            }
        }
}
