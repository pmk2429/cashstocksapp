package com.cash.stocksapp.models

import android.annotation.SuppressLint
import androidx.annotation.VisibleForTesting
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

/**
 * Data class that represents a model single StockData unit.
 */
@VisibleForTesting
data class StockData(
    @SerializedName("ticker")
    val tickerSymbol: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("current_price_cents")
    val tradingPrice: Int,
    @SerializedName("quantity")
    val quantity: String? = null,
    @SerializedName("current_price_timestamp")
    val timestamp: Int
)

/**
 * Ticker symbol is combination of currency and the ticker symbol.
 * For our case, since it's *USD* only it's hardcoded to prepend *$* at the start of the ticker symbol.
 * For example, in case of Block, the ticker will represent *$SQ*.
 */
fun StockData.ticker(): String = "$$tickerSymbol"

/**
 * Prepends `$` symbol to the Trading price after the Double value is evaluated.
 */
fun StockData.price(): String = "$".plus(tradingPrice.toDouble() / 100)

/**
 * Returns the String value of quantity with `Volume:` prepended to the actual quantity if present.
 */
fun StockData.lastTradeVolume(): String =
    if (!quantity.isNullOrEmpty()) "Volume: $quantity" else "null"

/**
 * Converts timestamp to human readable format.
 */
fun StockData.time(): String = timestamp.dateFormatter()

@SuppressLint("SimpleDateFormat")
fun Int.dateFormatter(): String {
    return SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date(this.toLong() * 1000)).toString()
}
