package com.der3.utils.connectivity

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn

object NetworkStatus {

    fun observeAsState(
        connectivityObserver: ConnectivityObserver,
        scope: CoroutineScope
    ): SharedFlow<NetworkState> {
        return connectivityObserver.observe()
            .shareIn(
                scope = scope,
                // sharing data when the first collector starts collecting and stops after 5 seconds of inactivity
                started = SharingStarted.WhileSubscribed(5_000),
            )
    }
}


fun NetworkState.isOffline(): Boolean =
    this == NetworkState.UnAvailable ||
            this == NetworkState.Lost