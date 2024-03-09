@file:Suppress("DEPRECATION")
package br.com.jwar.sharedbill.utility

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import androidx.annotation.RequiresApi
import br.com.jwar.sharedbill.core.utility.NetworkManager
import javax.inject.Inject

class NetworkManagerImpl @Inject constructor(
    private val context: Context
) : NetworkManager {
    override fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            isNetworkConnectedNewApi(connectivityManager)
        } else {
            isNetworkConnectedOldApi(connectivityManager)
        }
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun isNetworkConnectedNewApi(connectivityManager: ConnectivityManager): Boolean {
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return (networkCapabilities != null) && networkCapabilities.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_INTERNET
        ) && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

    @SuppressLint("MissingPermission")
    private fun isNetworkConnectedOldApi(connectivityManager: ConnectivityManager): Boolean {
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected && isNetworkWithInternetAccess(activeNetwork)
    }

    private fun isNetworkWithInternetAccess(networkInfo: NetworkInfo): Boolean {
        return when (networkInfo.type) {
            ConnectivityManager.TYPE_WIFI,
            ConnectivityManager.TYPE_MOBILE -> true
            else -> false
        }
    }
}
