package com.der3.shared.data.repo

import com.der3.shared.data.source.local.dao.FavoritesDao
import com.der3.shared.data.source.local.dao.RecycleBinDao
import com.der3.shared.data.source.local.entity.FavoriteEntity
import com.der3.shared.data.source.local.entity.RecycleBinEntity
import com.der3.shared.domain.repo.RecycleBinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecycleBinRepositoryImpl @Inject constructor(
    private val recycleBinDao: RecycleBinDao,
    private val favoritesDao: FavoritesDao
) : RecycleBinRepository {

    override fun getAllDeletedItems(): Flow<List<RecycleBinEntity>> {
        return recycleBinDao.getAllDeletedItems()
    }

    override suspend fun insertToRecycleBin(item: RecycleBinEntity) {
        recycleBinDao.insertToRecycleBin(item)
    }

    override suspend fun insertAll(items: List<RecycleBinEntity>) {
        recycleBinDao.insertAll(items)
    }

    override suspend fun deleteItemById(id: Int) {
        recycleBinDao.deletePermanently(id)
    }

    override suspend fun clearRecycleBin() {
        recycleBinDao.clearRecycleBin()
    }


}
