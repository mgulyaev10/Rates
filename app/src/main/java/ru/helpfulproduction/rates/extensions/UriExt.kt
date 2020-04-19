package ru.helpfulproduction.rates.extensions

import android.net.Uri

fun Uri.Builder.appendQueryParameters(parameters: Map<String, String>?) = apply {
    parameters?.forEach {
        appendQueryParameter(it.key, it.value)
    }
}