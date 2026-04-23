package com.mukesh.common

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.annotation.RequiresPermission
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkManager
    @Inject
    constructor(
        @ApplicationContext private var context: Context,
    ) {
        /**
         * Performs a one-time check to see if the device has an active internet connection.
         * Returns true if connected to Wi-Fi, Cellular, or Ethernet and has internet capability.
         */
        @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
        fun isNetworkAvailable(): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

            return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        }
    }
