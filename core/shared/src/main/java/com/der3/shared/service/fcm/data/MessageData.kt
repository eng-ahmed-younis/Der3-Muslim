package com.der3.shared.service.fcm.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


@Serializable
data class MessageData(
    val message: String,
    val type: String,
    val image: String? = null
){
    companion object{
        fun fromJson(jsonString: String):MessageData = Json.decodeFromString<MessageData>(jsonString)
    }
}