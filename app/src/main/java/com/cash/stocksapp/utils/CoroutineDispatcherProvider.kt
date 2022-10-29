package com.cash.stocksapp.utils

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatcherProvider {
    fun io(): CoroutineDispatcher
    fun main(): CoroutineDispatcher
    fun default(): CoroutineDispatcher
    fun unconfined(): CoroutineDispatcher
}
