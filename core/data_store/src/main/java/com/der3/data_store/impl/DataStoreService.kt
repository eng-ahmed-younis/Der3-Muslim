package com.der3.data_store.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreService @Inject constructor(
    val dataStore: DataStore<Preferences>
) {


    suspend fun set(key: String, value: Any?) {
        when (value) {
            is Int -> {
                val preferencesKey = intPreferencesKey(key)
                dataStore.edit { preferences ->
                    preferences[preferencesKey] = value
                }
            }

            is Long -> {
                val preferencesKey = longPreferencesKey(key)
                dataStore.edit { preferences ->
                    preferences[preferencesKey] = value
                }
            }

            is Float -> {
                val preferencesKey = floatPreferencesKey(key)
                dataStore.edit { preferences ->
                    preferences[preferencesKey] = value
                }
            }

            is String -> {
                val preferencesKey = stringPreferencesKey(key)
                dataStore.edit { preferences ->
                    preferences[preferencesKey] = value
                }
            }

            is Boolean -> {
                val preferencesKey = booleanPreferencesKey(key)
                dataStore.edit { preferences ->
                    preferences[preferencesKey] = value
                }
            }

            else -> throw UnsupportedOperationException("Data store type not implemented type")
        }
    }

    inline operator fun <reified T> get(
        key: String,
        defaultValue: T
    ): Flow<T> {
        return when (T::class) {
            Int::class -> {
                val preferencesKey = intPreferencesKey(key)
                dataStore.data.map { preferences ->
                    preferences[preferencesKey] as? T ?: defaultValue
                }
            }

            Long::class -> {
                val preferencesKey = longPreferencesKey(key)
                dataStore.data.map { preferences ->
                    preferences[preferencesKey] as? T ?: defaultValue
                }
            }

            Float::class -> {
                val preferencesKey = floatPreferencesKey(key)
                dataStore.data.map { preferences ->
                    preferences[preferencesKey] as? T ?: defaultValue
                }
            }

            String::class -> {
                val preferencesKey = stringPreferencesKey(key)
                dataStore.data.map { preferences ->
                    preferences[preferencesKey] as? T ?: defaultValue
                }
            }

            Boolean::class -> {
                val preferencesKey = booleanPreferencesKey(key)
                dataStore.data.map { preferences ->
                    preferences[preferencesKey] as? T ?: defaultValue
                }
            }

            else -> throw UnsupportedOperationException("Data store type not implemented type")
        }
    }

    suspend fun clearData() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    // NEW: Remove specific keys by string names
    suspend fun remove(key: String) {
        dataStore.edit { preferences ->
            // Try to find and remove the key by checking all possible types
            listOf(
                stringPreferencesKey(key),
                intPreferencesKey(key),
                booleanPreferencesKey(key),
                longPreferencesKey(key),
                floatPreferencesKey(key)
            ).forEach { prefKey ->
                if (preferences.contains(prefKey)) {
                    preferences.remove(prefKey)
                }
            }
        }
    }

    // NEW: Remove multiple keys at once
    suspend fun removeAll(vararg keys: String) {
        dataStore.edit { preferences ->
            keys.forEach { key ->
                listOf(
                    stringPreferencesKey(key),
                    intPreferencesKey(key),
                    booleanPreferencesKey(key),
                    longPreferencesKey(key),
                    floatPreferencesKey(key)
                ).forEach { prefKey ->
                    if (preferences.contains(prefKey)) {
                        preferences.remove(prefKey)
                    }
                }
            }
        }
    }

    // NEW: Clear all except the specified keys
    suspend fun clearAllExcept(keep: Set<String>) {
        dataStore.edit { preferences ->
            val keysToRemove = mutableListOf<Preferences.Key<*>>()

            // Collect all keys that are NOT in the keep set
            preferences.asMap().keys.forEach { prefKey ->
                val keyName = prefKey.name
                if (keyName !in keep) {
                    keysToRemove.add(prefKey)
                }
            }

            // Remove all collected keys
            keysToRemove.forEach { keyToRemove ->
                preferences.remove(keyToRemove)
            }
        }
    }

}