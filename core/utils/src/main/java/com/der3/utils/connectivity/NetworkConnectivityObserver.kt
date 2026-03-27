package com.der3.utils.connectivity

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class NetworkConnectivityObserver(
    context: Context
) : ConnectivityObserver {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun observe(): Flow<NetworkState> = callbackFlow {
        trySend(currentState())

        val callback = object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                trySend(NetworkState.Available)
            }

            override fun onLost(network: Network) {
                trySend(NetworkState.Lost)
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                trySend(NetworkState.Losing)
            }

            override fun onUnavailable() {
                trySend(NetworkState.UnAvailable)
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(request, callback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }.distinctUntilChanged()


    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    private fun currentState(): NetworkState {
        val network = connectivityManager.activeNetwork
            ?: return NetworkState.UnAvailable

        val caps = connectivityManager.getNetworkCapabilities(network)
            ?: return NetworkState.UnAvailable

        return if (caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
            NetworkState.Available
        } else {
            NetworkState.UnAvailable
        }
    }
}