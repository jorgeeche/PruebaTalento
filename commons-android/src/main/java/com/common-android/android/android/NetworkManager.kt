package com.commons.android

import android.content.Context
import android.net.ConnectivityManager

class NetworkManager(private val context: Context) {

    fun isThereConnectionToInternet(): Boolean {
        val activeNetworkInfo = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}