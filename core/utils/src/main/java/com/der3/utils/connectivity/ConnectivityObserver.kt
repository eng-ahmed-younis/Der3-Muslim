package com.der3.utils.connectivity

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observe () : Flow<NetworkState>
}