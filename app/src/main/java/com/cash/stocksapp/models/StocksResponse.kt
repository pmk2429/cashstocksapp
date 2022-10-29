package com.cash.stocksapp.models

import com.google.gson.annotations.SerializedName

data class StocksResponse(
    @SerializedName("stocks")
    val stocks: List<StockData>
)
