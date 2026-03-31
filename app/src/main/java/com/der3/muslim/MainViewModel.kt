package com.der3.muslim

import android.content.Context
import android.content.res.Configuration
import androidx.lifecycle.ViewModel
import com.der3.data_store.api.DataStoreRepository
import com.der3.model.AppStyle
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.der3.screens.Screens
import com.der3.screens.Der3NavigationRoute
import android.content.Intent
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

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
        dataStoreRepository.appStyleFlow.onEach {
            appStyle.value = when (it) {
                AppStyle.LIGHT.value -> AppStyle.LIGHT
                AppStyle.DARK.value -> AppStyle.DARK
                else -> AppStyle.SYSTEM
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