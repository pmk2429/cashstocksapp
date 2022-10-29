package com.cash.stocksapp.service

import com.cash.stocksapp.Constants.EMPTY_PORTFOLIO_DATA
import com.cash.stocksapp.Constants.MALFORMED_PORTFOLIO_DATA
import com.cash.stocksapp.Constants.PORTFOLIO_DATA
import com.cash.stocksapp.models.StocksResponse
import retrofit2.http.GET

/**
 * Communicates with the Stocks App backend to obtain data using the coroutines.
 */
interface StocksApiService {
    /**
     * Returns the Stocks data feed.
     */
    @GET(PORTFOLIO_DATA)
    suspend fun getStocksData(): StocksResponse

    /**
     * Returns malformed Portfolio data.
     */
    @GET(MALFORMED_PORTFOLIO_DATA)
    suspend fun getMalformedStocksData(): StocksResponse

    /**
     * Returns empty Portfolio data.
     */
    @GET(EMPTY_PORTFOLIO_DATA)
    suspend fun getEmptyStocksData(): StocksResponse
}
