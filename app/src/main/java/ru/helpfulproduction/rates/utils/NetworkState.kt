package ru.helpfulproduction.rates.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi

object NetworkState {

    fun isConnected(context: Context?): Boolean {
        if (context == null) {
            return false
        }
        val manager = getConnectivityManager(context)
        return if (Device.isAtLeastM()) {
            isConnectedImpl(manager)
        } else {
            isConnectedImplBeforeM(manager)
        }
    }

    private fun isConnectedImplBeforeM(manager: ConnectivityManager): Boolean {
        return manager.activeNetworkInfo?.isConnected == true
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isConnectedImpl(manager: ConnectivityManager): Boolean {
        val capabilities = manager.getNetworkCapabilities(manager.activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    private fun getConnectivityManager(context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

}