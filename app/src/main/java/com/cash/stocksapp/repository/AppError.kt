package com.cash.stocksapp.repository

open class AppError(
    override val message: String? = null,
    override val cause: Throwable? = null
) : Throwable(message, cause)
