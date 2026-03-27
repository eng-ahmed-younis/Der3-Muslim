package com.der3.shared.data.repo

import com.der3.shared.data.mappers.toDomain
import com.der3.shared.data.mappers.toEntity
import com.der3.shared.data.mappers.toMasbahaAzkarDomainModel
import com.der3.shared.data.source.MasbahaRemoteDataSource
import com.der3.shared.data.source.local.dao.MasbahaAzkarDao
import com.der3.shared.domain.model.MasbahaAzkar
import com.der3.shared.domain.repo.MasbahaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MasbahaRepositoryImpl @Inject constructor(
    private val remoteDataSource: MasbahaRemoteDataSource,
    private val localDataSource: MasbahaAzkarDao
) : MasbahaRepository {

    override suspend fun getRemoteVersion(): Int {
        return remoteDataSource.getVersion()
    }

    override suspend fun getRemoteAzkars(): List<MasbahaAzkar> {
        return remoteDataSource.getAzkars().map { it.toMasbahaAzkarDomainModel() }
    }

    override fun getLocalAzkars(): Flow<List<MasbahaAzkar>> {
        return localDataSource.getAllAzkars().map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getLocalAzkarSize(): Flow<Int> {
        return localDataSource.getAzkarCount()
    }

    override suspend fun updateLocalAzkars(azkars: List<MasbahaAzkar>) {
        localDataSource.insertAzkars(azkars.map { it.toEntity() })
    }

    override suspend fun clearLocalAzkars() {
        localDataSource.deleteAllAzkars()
    }
}