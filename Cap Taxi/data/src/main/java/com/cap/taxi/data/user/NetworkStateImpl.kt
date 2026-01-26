package com.cap.taxi.data.user

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.cap.taxi.domain.data.INetworkState

class NetworkStateImpl(
    private val context: Context
): INetworkState {

    override fun isNetworkConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        if (network != null) {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            if (networkCapabilities != null) {
                return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            }
        }
        return false
    }
}