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
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private var appStyle: MutableStateFlow<AppStyle> = MutableStateFlow(AppStyle.SYSTEM)
    val appStyleFlow = appStyle.asStateFlow()


    init {
        appStyle.value = getSystemTheme()
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

}