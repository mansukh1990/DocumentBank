package com.example.documentbank.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.documentbank.BaseApplication

object ResponseCode {

    const val SUCCESS_CODE = 200
    const val ERROR_CODE = 400
    const val TOKEN_EXPIRED_CODE = 401
    const val APP_UPDATE_CODE = 801
    const val ANY_CODE = -1
    const val NETWORK_CODE = 101
    const val SYNC_VALIDATION = 207


    fun checkNetworkAvailability(): Boolean {
        val connectivityManager = BaseApplication.applicationContext()
            ?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        return capabilities != null && (
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                )
    }

}