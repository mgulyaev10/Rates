package ru.helpfulproduction.rates.api.core

import ru.helpfulproduction.rates.network.OkHttpExecutor
import ru.helpfulproduction.rates.network.OkHttpMethod
import ru.helpfulproduction.rates.network.OkHttpProvider

object ApiManager {
    private val okHttpExecutor = lazy { OkHttpExecutor(OkHttpProvider()) }

    fun execute(method: OkHttpMethod): String {
        return okHttpExecutor.value.execute(method)
    }
}