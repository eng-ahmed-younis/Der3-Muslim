package com.der3.sections.domain.model

import com.der3.sections.domain.repository.IPrayerRepository


sealed class PrayerTimesResult<out T> {
    data class Success<T>(val data: T) : PrayerTimesResult<T>()
    data class Error(val message: String, val code: Int? = null) : PrayerTimesResult<Nothing>()
    object Loading : PrayerTimesResult<Nothing>()

    fun isLoading() = this is Loading
    fun isSuccess() = this is Success
    fun isError() = this is Error

    fun getOrNull(): T? = (this as? Success)?.data
}

// Extension function to convert Repository Result to UseCase Result
fun <T> IPrayerRepository.Result<T>.toPrayerTimesResult(): PrayerTimesResult<T> = when (this) {
    is IPrayerRepository.Result.Success -> PrayerTimesResult.Success(data)
    is IPrayerRepository.Result.Error -> PrayerTimesResult.Error(message, code)
    is IPrayerRepository.Result.Loading -> PrayerTimesResult.Loading
}