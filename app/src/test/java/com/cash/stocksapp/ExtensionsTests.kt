package com.cash.stocksapp

import com.cash.stocksapp.models.StockData
import com.cash.stocksapp.models.price
import com.cash.stocksapp.models.ticker
import com.cash.stocksapp.models.time
import org.junit.Assert.*
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class ExtensionsTests {

    @Test
    fun `test when StockData ticker prepends with $ symbol, should be true`() {
        val stockData = getStockData()
        val stockTicker = stockData.ticker()
        assertEquals(stockTicker, "\$SQ")
    }

    @Test
    fun `test when StockData ticker prepends with any other symbol, should be false`() {
        val stockData = getStockData()
        val stockTicker = stockData.ticker()
        assertFalse(stockTicker == "#SQ")
    }

    @Test
    fun `test when StockData price is calculated, $ symbol is prepended`() {
        val stockData = getStockData()
        val stockPrice = stockData.price()
        assertTrue(stockPrice.first() == '$')
    }

    @Test
    fun `test when StockData price is calculated, the price is in double format`() {
        val stockData = getStockData()
        val stockPrice = stockData.price().drop(1).toDoubleOrNull()
        assertEquals(61.29, stockPrice!!, 0.0)
    }

    @Test
    fun `test when StockData timestamp is calculated, the date time is true`() {
        val stockData = getStockData()
        val dateTime = stockData.time()
        val expectedTime =
            SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date(1636657688.toLong() * 1000))
                .toString()
        assertEquals(expectedTime, dateTime)
    }

    private fun getStockData() =
        StockData(
            "SQ",
            "Block Inc",
            "USD",
            6129,
            "10",
            1636657688
        )
}
