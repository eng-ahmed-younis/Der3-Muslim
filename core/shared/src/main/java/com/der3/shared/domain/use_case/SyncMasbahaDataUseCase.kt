package com.der3.shared.domain.use_case

import com.der3.data_store.api.DataStoreRepository
import com.der3.shared.domain.repo.MasbahaRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface SyncMasbahaDataUseCase {
    suspend operator fun invoke()
}

class SyncMasbahaDataUseCaseImpl @Inject constructor(
    private val repository: MasbahaRepository,
    private val dataStoreRepository: DataStoreRepository
) : SyncMasbahaDataUseCase {

    override suspend fun invoke() {
        try {
            val remoteVersion = repository.getRemoteVersion()
            val localVersion = dataStoreRepository.masbahaDataVersion
            val localAzkars = repository.getLocalAzkars().first()

            if (remoteVersion > localVersion || localAzkars.isEmpty()) {
                val remoteAzkars = repository.getRemoteAzkars()
                
                repository.clearLocalAzkars()
                repository.updateLocalAzkars(remoteAzkars)
                
                dataStoreRepository.masbahaDataVersion = remoteVersion
            }
        } catch (e: Exception) {
            // Handle or log sync error
            e.printStackTrace()
        }
    }
}