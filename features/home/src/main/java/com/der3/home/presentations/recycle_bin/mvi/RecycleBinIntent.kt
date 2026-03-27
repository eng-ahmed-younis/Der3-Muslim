package com.der3.home.presentations.recycle_bin.mvi

import com.der3.mvi.MviIntent

sealed interface RecycleBinIntent : MviIntent {
    data class RestoreItem(val id: Int) : RecycleBinIntent
    data class DeletePermanently(val id: Int) : RecycleBinIntent
    data object EmptyBin : RecycleBinIntent
    data object Retry : RecycleBinIntent
    data object DismissError : RecycleBinIntent
    data object Back : RecycleBinIntent
}
