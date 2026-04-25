package com.der3.muslims

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.der3.data_store.api.DataStoreRepository
import com.der3.model.AppStyle
import com.der3.screens.Der3NavigationRoute
import com.der3.screens.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private var appStyle: MutableStateFlow<AppStyle> = MutableStateFlow(AppStyle.SYSTEM)
    val appStyleFlow = appStyle.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<Screens>()
    val navigationEvent = _navigationEvent.asSharedFlow()


    init {
        observeAppStyle()
    }

    private fun observeAppStyle() {
        dataStoreRepository.appStyleFlow.onEach { styleValue ->
            appStyle.value = when (styleValue) {
                AppStyle.LIGHT.value -> AppStyle.LIGHT
                AppStyle.DARK.value -> AppStyle.DARK
                else -> {
                    // 1. Determine the actual theme based on system settings
                    val determinedTheme = if (isSystemDark()) AppStyle.DARK else AppStyle.LIGHT

                    // 2. Update the DataStore manually.
                    // This update will trigger 'appStyleFlow' again automatically.
                    dataStoreRepository.appStyle = determinedTheme.value

                    // 3. Return the determined theme for the immediate UI state
                    determinedTheme
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getSystemTheme(): AppStyle {
        dataStoreRepository.appStyle.let {
            return when (it) {
                AppStyle.LIGHT.value -> AppStyle.LIGHT
                AppStyle.DARK.value -> AppStyle.DARK
                else -> AppStyle.SYSTEM
            }
        }
    }

    fun handleIntent(intent: Intent?) {
        intent?.let {
            handleDeepLink(intent = intent)
        }
    }


    private fun isSystemDark(): Boolean {
        return context.resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK ==
                android.content.res.Configuration.UI_MODE_NIGHT_YES
    }


    private fun handleDeepLink(intent: Intent?) {
        val data: Uri? = intent?.data
        if (data != null && data.pathSegments.contains("notifications")) {
            val notificationIdString = data.lastPathSegment
            val notificationId = notificationIdString?.toLongOrNull() ?: 0L

            Log.d("DeepLink", "Navigating to notification ID: $notificationId")

            viewModelScope.launch {
                _navigationEvent.emit(Der3NavigationRoute.NotificationScreen(notificationId = notificationId))
            }
        }
    }
}