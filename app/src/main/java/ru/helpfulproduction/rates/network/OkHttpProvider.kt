package ru.helpfulproduction.rates.network

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class OkHttpProvider {
    private var httpClient: OkHttpClient? = null

    fun getClient(): OkHttpClient {
        val httpClient = httpClient
        if (httpClient == null) {
            val client = OkHttpClient.Builder()
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build()
            this.httpClient = client
            return client
        }
        return httpClient
    }

    private companion object {
        const val DEFAULT_CONNECT_TIMEOUT_SECONDS = 20L
        const val DEFAULT_READ_TIMEOUT_SECONDS = 30L
    }

}