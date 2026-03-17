package com.der3.shared.domain.use_case.masbaha

import com.der3.data_store.api.DataStoreRepository
import com.der3.model.SyncState
import com.der3.shared.domain.repo.MasbahaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface SyncMasbahaDataUseCase {
    operator fun invoke(): Flow<SyncState>
}

class SyncMasbahaDataUseCaseImpl @Inject constructor(
    private val repository: MasbahaRepository,
    private val dataStoreRepository: DataStoreRepository
) : SyncMasbahaDataUseCase {

    override fun invoke(): Flow<SyncState> = flow {
        emit(SyncState.SYNCING)
        try {
            val remoteVersion = repository.getRemoteVersion()
            val localVersion = dataStoreRepository.masbahaDataVersion
            val localAzkars = repository.getLocalAzkars().first()

            if (remoteVersion > localVersion || localAzkars.isEmpty() || localVersion == 0) {
                val remoteAzkars = repository.getRemoteAzkars()

                repository.clearLocalAzkars()
                repository.updateLocalAzkars(remoteAzkars)

                dataStoreRepository.masbahaDataVersion = remoteVersion
            }
            emit(SyncState.SUCCESS)
        } catch (e: Exception) {
            // Handle or log sync error
            e.printStackTrace()
            emit(SyncState.FAILED)
        }
    }
}
