package com.der3.shared.domain.repo

import com.der3.shared.domain.model.MasbahaAzkar
import kotlinx.coroutines.flow.Flow

interface MasbahaRepository {
    // Remote
    suspend fun getRemoteVersion(): Int
    suspend fun getRemoteAzkars(): List<MasbahaAzkar>

    // Local
    fun getLocalAzkars(): Flow<List<MasbahaAzkar>>
    suspend fun updateLocalAzkars(azkars: List<MasbahaAzkar>)
    suspend fun clearLocalAzkars()
}