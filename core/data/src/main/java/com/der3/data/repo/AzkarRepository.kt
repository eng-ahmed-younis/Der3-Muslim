package com.der3.data.repo

import android.content.Context
import com.der3.data.model.AzkarCategory
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
class AzkarRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val json = Json { ignoreUnknownKeys = true }

    private var cachedCategories: List<AzkarCategory>? = null

    private suspend fun loadJson(): List<AzkarCategory> {
        return cachedCategories ?: run {
            val result = withContext(Dispatchers.IO) {
                val jsonString = context.assets
                    .open(AZKAR_FILE_NAME)
                    .bufferedReader()
                    .use { it.readText() }
                json.decodeFromString<List<AzkarCategory>>(jsonString)
            }
            cachedCategories = result
            result
        }
    }

    fun getAllCategories(): Flow<List<AzkarCategory>> = flow {
        emit(loadJson())
    }

    fun getCategoryById(id: Int): Flow<AzkarCategory?> = flow {
        emit(loadJson().find { it.id == id })
    }
}