package com.der3.home.presentations.notification

import androidx.lifecycle.viewModelScope
import com.der3.home.domain.model.NotificationItem
import com.der3.home.domain.model.NotificationType
import com.der3.home.presentations.notification.mvi.NotificationAction
import com.der3.home.presentations.notification.mvi.NotificationIntent
import com.der3.home.presentations.notification.mvi.NotificationReducer
import com.der3.home.presentations.notification.mvi.NotificationState
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    reducer: NotificationReducer
) : MviBaseViewModel<NotificationState, NotificationAction, NotificationIntent>(
    initialState = NotificationState(),
    reducer = reducer
) {

    init {
        onIntent(NotificationIntent.LoadNotifications)
    }

    override fun handleIntent(intent: NotificationIntent) {
        when (intent) {
            is NotificationIntent.LoadNotifications -> loadNotifications()
            is NotificationIntent.Retry -> loadNotifications()
            is NotificationIntent.Back -> {
                onEffect(MviEffect.Navigate(screen = Screens.Back()))
            }
            is NotificationIntent.DismissError -> {
                onAction(NotificationAction.DismissError)
            }
        }
    }

    private fun loadNotifications() {
        viewModelScope.launch {
            onAction(NotificationAction.Loading)
            try {
                // Mocking data for now as repository is still being setup
                val today = listOf(
                    NotificationItem(
                        id = "1",
                        title = "حان موعد صلاة العصر",
                        description = "الصلاة خير من النوم، أقم صلاتك تنعم بحياتك.",
                        time = "الآن",
                        type = NotificationType.DAILY
                    ),
                    NotificationItem(
                        id = "2",
                        title = "أذكار المساء",
                        description = "لا تنسى وردك من أذكار المساء لتحصين نفسك.",
                        time = "منذ ساعتين",
                        type = NotificationType.DAILY
                    )
                )

                val yesterday = listOf(
                    NotificationItem(
                        id = "3",
                        title = "تحديث جديد متوفر",
                        description = "تم إضافة ميزات جديدة في المسبحة الإلكترونية، استكشفها الآن.",
                        time = "بالأمس",
                        type = NotificationType.DAILY
                    )
                )

                onAction(
                    NotificationAction.Success(
                        today = today,
                        yesterday = yesterday,
                        aya = "فَاذْكُرُونِي أَذْكُرْكُمْ وَاشْكُرُوا لِي وَلَا تَكْفُرُونِ"
                    )
                )
            } catch (e: Exception) {
                onAction(NotificationAction.Error(e.message ?: "An unknown error occurred"))
            }
        }
    }
}
