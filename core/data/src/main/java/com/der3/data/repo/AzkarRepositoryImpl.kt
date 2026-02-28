package com.der3.data.repo

import android.content.Context
import com.der3.data.model.AzkarCategory
import com.der3.data.model.AzkarItem
import com.der3.data.repo.api.AzkarItemWithCategory
import com.der3.data.repo.api.AzkarRepository
import com.der3.data.repo.api.AzkarSearchResult
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

const val AZKAR_FILE_NAME = "adhkar.json"

@Singleton
class AzkarRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AzkarRepository {

    private val json = Json { ignoreUnknownKeys = true }
    private var cachedCategories: List<AzkarCategory>? = null

    // ─── Core Loader ─────────────────────────────────────────────────────────

    private suspend fun loadJson(): List<AzkarCategory> {
        return cachedCategories ?: withContext(Dispatchers.IO) {
            val jsonString = context.assets
                .open(AZKAR_FILE_NAME)
                .bufferedReader()
                .use { it.readText() }
            json.decodeFromString<List<AzkarCategory>>(jsonString)
        }.also { cachedCategories = it }
    }

    // ─── Basic Queries ────────────────────────────────────────────────────────

    override fun getAllCategories(): Flow<List<AzkarCategory>> = flow {
        emit(loadJson())
    }

    override fun getCategoryById(id: Int): Flow<AzkarCategory?> = flow {
        emit(loadJson().find { it.id == id })
    }

    // ─── Search Cases ─────────────────────────────────────────────────────────

    /**
     * Case 1: Search by category name only
     * e.g. query = "صباح" → returns categories whose name contains the query
     */
    override fun searchCategories(query: String): Flow<List<AzkarCategory>> = flow {
        if (query.isBlank()) {
            emit(loadJson())
            return@flow
        }
        val normalizedQuery = query.trim().normalizeArabic()
        emit(
            loadJson().filter { category ->
                category.category.normalizeArabic().contains(normalizedQuery)
            }
        )
    }

    /**
     * Case 2: Search inside azkar text only
     * e.g. query = "بسم الله" → returns all AzkarItem whose text matches
     */
    override fun searchItems(query: String): Flow<List<AzkarItem>> = flow {
        if (query.isBlank()) {
            emit(emptyList())
            return@flow
        }
        val normalizedQuery = query.trim().normalizeArabic()
        val results = mutableListOf<AzkarItem>()
        loadJson().forEach { category ->
            category.items.forEach { item ->
                if (item.text.normalizeArabic().contains(normalizedQuery)) {
                    results.add(item)
                }
            }
        }
        emit(results)
    }

    /**
     * Case 3: Search everything — categories + azkar text
     * Returns a combined AzkarSearchResult with:
     *   - matchedCategories: categories whose name matches
     *   - matchedItems: azkar items that match, each paired with its parent category
     *
     * Handles:
     *   - empty query → return all categories, no items
     *   - blank/whitespace → treated as empty
     *   - partial Arabic word match (e.g. "الله" matches "بسم الله")
     *   - tashkeel-insensitive (حَمْدُ matches حمد)
     *   - duplicate item guard (item won't appear twice even if category also matches)
     */
    override fun searchAll(query: String): Flow<AzkarSearchResult> = flow {
        if (query.isBlank()) {
            emit(AzkarSearchResult(matchedCategories = loadJson()))
            return@flow
        }

        val normalizedQuery = query.trim().normalizeArabic()
        val categories = loadJson()

        val matchedCategories = mutableListOf<AzkarCategory>()
        val matchedItems = mutableListOf<AzkarItemWithCategory>()

        categories.forEach { category ->
            val categoryNameMatches = category.category
                .normalizeArabic()
                .contains(normalizedQuery)

            if (categoryNameMatches) {
                matchedCategories.add(category)
            }

            // Always search inside items regardless of category match
            category.items.forEach { item ->
                if (item.text.normalizeArabic().contains(normalizedQuery)) {
                    matchedItems.add(AzkarItemWithCategory(category = category, item = item))
                }
            }
        }

        emit(
            AzkarSearchResult(
                matchedCategories = matchedCategories,
                matchedItems = matchedItems
            )
        )
    }

    // ─── Bonus Queries ────────────────────────────────────────────────────────

    /**
     * Case 4: Get all items that require a specific count (e.g. repeated 100 times)
     */
   override fun getItemsByCount(count: Int): Flow<List<AzkarItemWithCategory>> = flow {
        val results = mutableListOf<AzkarItemWithCategory>()
        loadJson().forEach { category ->
            category.items
                .filter { it.count == count }
                .forEach { results.add(AzkarItemWithCategory(category, it)) }
        }
        emit(results)
    }

    /**
     * Case 5: Get a specific item by category id + item id
     */
    override fun getItemById(categoryId: Int, itemId: Int): Flow<AzkarItem?> = flow {
        val category = loadJson().find { it.id == categoryId }
        emit(category?.items?.find { it.id == itemId })
    }

    /**
     * Case 6: Get total azkar count across all categories
     */
    override fun getTotalAzkarCount(): Flow<Int> = flow {
        emit(loadJson().sumOf { it.items.size })
    }



}

// ─── Arabic Normalization ─────────────────────────────────────────────────────
// Strips tashkeel (diacritics) and normalizes alef variants for
// fuzzy Arabic search. This ensures "أذكار" matches "اذكار" etc.

private fun String.normalizeArabic(): String {
    return this
        // Remove tashkeel (harakat)
        .replace(Regex("[\u064B-\u065F\u0670]"), "")
        // Normalize alef variants → bare alef
        .replace(Regex("[إأآا]"), "ا")
        // Normalize teh marbuta
        .replace('ة', 'ه')
        // Normalize yeh variants
        .replace('ى', 'ي')
        .trim()
}



/*
 * ============================
 *   Summary of Search Cases
 * ============================
 *
 * Case               | Method                     | Matches
 * ------------------------------------------------------------------
 * Empty query        | searchAll("")              | Returns all categories
 * Category name      | searchCategories("صباح")   | Category names only
 * Azkar text         | searchItems("بسم الله")    | Item text only
 * Both               | searchAll("الله")          | Categories + items combined
 * By count           | getItemsByCount(100)       | Items repeated N times
 * By ID              | getItemById(1, 3)          | Specific item in specific category
 * Total count        | getTotalAzkarCount()       | Stats
 *
 */