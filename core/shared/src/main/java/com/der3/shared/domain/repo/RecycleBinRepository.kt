package com.der3.shared.domain.repo

import com.der3.shared.data.source.local.entity.FavoriteEntity
import com.der3.shared.data.source.local.entity.RecycleBinEntity
import kotlinx.coroutines.flow.Flow

interface RecycleBinRepository {
    fun getAllDeletedItems(): Flow<List<RecycleBinEntity>>
    suspend fun insertToRecycleBin(item: RecycleBinEntity)
    suspend fun insertAll(items: List<RecycleBinEntity>)

    suspend fun deleteItemById(id: Int)
    suspend fun clearRecycleBin()
}
