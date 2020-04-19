package ru.helpfulproduction.rates.network

import okhttp3.Request

class OkHttpExecutor(
    private val okHttpProvider: OkHttpProvider
) {

    fun execute(method: OkHttpMethod): String {
        val request = method.toRequest()
        return execute(request)
    }

    private fun execute(request: Request): String {
        okHttpProvider.getClient()
            .newCall(request)
            .execute().use {
                return it.body?.string() ?: ""
            }
    }

}