package com.der3.utils

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


val json = Json { 
    ignoreUnknownKeys = true // Doesn't crash if the JSON has more fields than your class
    isLenient = true // Accepts malformed JSON (like missing quotes)
    encodeDefaults = true  // Includes default values in the generated JSON string
}


inline fun <reified T> T.toJson(): String {
    return json.encodeToString(this)
}

inline fun <reified T> String.parseJson(): T? {
    return try {
        json.decodeFromString<T>(this)
    } catch (e: Exception) {
        null
    }
}

// Convenience functions specifically for Lists
inline fun <reified T> List<T>.toJsonList(): String {
    return json.encodeToString(this)
}

inline fun <reified T> String.fromJsonList(): List<T>? {
    return try {
        json.decodeFromString<List<T>>(this)
    } catch (e: Exception) {
        null
    }
}