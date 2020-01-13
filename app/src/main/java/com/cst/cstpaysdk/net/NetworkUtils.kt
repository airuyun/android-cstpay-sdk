package com.cst.cstpaysdk.net


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.net.Network
import android.net.NetworkCapabilities

object NetworkUtils{

    fun isConnected(context: Context):Boolean{
        val connectManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val allNetworks: Array<Network> = connectManager.allNetworks
            for (network in allNetworks) {
                val capabilities: NetworkCapabilities? = connectManager.getNetworkCapabilities(network)
                capabilities?.let {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        return true
                    }
                }
            }
        } else {
            val networkInfo : NetworkInfo?= connectManager.activeNetworkInfo
            if(networkInfo!=null) {
                return networkInfo.isAvailable&& networkInfo.isConnected
            }
        }
        return false
    }
}