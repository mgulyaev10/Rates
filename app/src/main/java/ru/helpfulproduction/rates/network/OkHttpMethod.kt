package ru.helpfulproduction.rates.network

import android.net.Uri
import okhttp3.Request
import ru.helpfulproduction.rates.extensions.appendQueryParameters

data class OkHttpMethod(
    private val scheme: String,
    private val authority: String,
    private val path: String,
    private val queryParams: Map<String, String>? = null
) {

    fun toRequest(): Request {
        val uri = Uri.Builder()
            .scheme(scheme)
            .authority(authority)
            .path(path)
            .appendQueryParameters(queryParams)
        return Request.Builder()
            .url(uri.toString())
            .build()
    }

}