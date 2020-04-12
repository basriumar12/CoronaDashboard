package com.basbas.lawanqfid.utama.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Service{
    companion object {
        // init Retrofit base server instance
//        val redditClient by lazy { ApiService.invoke(REDDIT_BASE_URL) }
//        val stackClient by lazy { ApiService.invoke(STACK_BASE_URL) }

        private val loggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        operator fun invoke(baseUrl: String): ApiService {
            val client = OkHttpClient.Builder().apply {
                /**addNetworkInterceptor(StethoInterceptor()) */
                addNetworkInterceptor(loggingInterceptor)
                connectTimeout(10, TimeUnit.MINUTES)
                readTimeout(10, TimeUnit.MINUTES)
                writeTimeout(10, TimeUnit.MINUTES)
            }.build()

            return Retrofit.Builder()
                    .client(client)
                    .baseUrl(baseUrl)
                   // .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService::class.java)
        }
    }
}