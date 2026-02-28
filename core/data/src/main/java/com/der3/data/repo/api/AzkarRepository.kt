package com.der3.data.repo.api


import com.der3.data.model.AzkarCategory
import com.der3.data.model.AzkarItem
import kotlinx.coroutines.flow.Flow

interface AzkarRepository {
    fun getAllCategories(): Flow<List<AzkarCategory>>
    fun getCategoryById(id: Int): Flow<AzkarCategory?>
    fun searchCategories(query: String): Flow<List<AzkarCategory>>
    fun searchItems(query: String): Flow<List<AzkarItem>>
    fun searchAll(query: String): Flow<AzkarSearchResult>
    fun getItemsByCount(count: Int): Flow<List<AzkarItemWithCategory>>  // ← add
    fun getTotalAzkarCount(): Flow<Int>
    fun getItemById(categoryId: Int, itemId: Int): Flow<AzkarItem?>  // ← add

}


data class AzkarSearchResult(
    val matchedCategories: List<AzkarCategory> = emptyList(),
    val matchedItems: List<AzkarItemWithCategory> = emptyList()
)

data class AzkarItemWithCategory(
    val category: AzkarCategory,
    val item: AzkarItem
)