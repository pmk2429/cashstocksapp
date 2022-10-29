package com.cash.stocksapp.di

import com.cash.stocksapp.Constants.BASE_URL
import com.cash.stocksapp.service.StocksApiService
import com.cash.stocksapp.utils.CoroutineDispatcherProvider
import com.google.gson.GsonBuilder
import com.tps.challenge.util.DefaultDispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Provides Network communication and Coroutine related instances.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideCoroutineTPSService(): StocksApiService {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
        return retrofit.create(StocksApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcherProvider {
        return DefaultDispatcherProvider
    }
}
