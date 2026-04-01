package com.der3.model

enum class NotificationType(val value: String) {
    GENERAL(value = "general"),
    DAILY(value = "daily");


    companion object {
        fun fromValue(value: String): NotificationType {
            return entries.find { it.value == value } ?: GENERAL
        }
    }
}
