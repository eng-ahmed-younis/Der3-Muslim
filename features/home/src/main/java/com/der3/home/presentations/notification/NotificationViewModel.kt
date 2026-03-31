package com.der3.home.presentations.notification

import androidx.lifecycle.viewModelScope
import com.der3.home.di.factory.NotificationViewModelFactory
import com.der3.home.domain.model.NotificationItem
import com.der3.home.domain.model.NotificationType
import com.der3.home.presentations.notification.mvi.NotificationAction
import com.der3.home.presentations.notification.mvi.NotificationIntent
import com.der3.home.presentations.notification.mvi.NotificationReducer
import com.der3.home.presentations.notification.mvi.NotificationState
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import com.der3.shared.params.NotificationParams
import com.der3.shared.domain.use_case.notification.GetAllNotificationsUseCase
import com.der3.utils.TimeFormatUtils
import java.util.Calendar
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = NotificationViewModelFactory::class)
class NotificationViewModel @AssistedInject constructor(
    @Assisted params: NotificationParams,
    private val getAllNotificationsUseCase: GetAllNotificationsUseCase,
    reducer: NotificationReducer
) : MviBaseViewModel<NotificationState, NotificationAction, NotificationIntent>(
    initialState = NotificationState(),
    reducer = reducer
) {

    init {
        loadNotifications()
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

        getAllNotificationsUseCase.invoke()
            .onStart {
                onAction(NotificationAction.Loading(isLoading = true))
            }.onEach { notifications ->
                val today = Calendar.getInstance()
                val yesterday = Calendar.getInstance().apply { add(Calendar.DATE, -1) }

                val todayList = mutableListOf<NotificationItem>()
                val yesterdayList = mutableListOf<NotificationItem>()

                notifications.forEach { entity ->
                    val item = NotificationItem(
                        id = entity.id.toString(),
                        title = entity.title,
                        description = entity.body,
                        time = TimeFormatUtils.getRelativeTimeSpanString(entity.timestamp),
                        type = NotificationType.GENERAL
                    )

                    val itemCalendar =
                        Calendar.getInstance().apply { timeInMillis = entity.timestamp }

                    if (isSameDay(itemCalendar, today)) {
                        todayList.add(item)
                    } else if (isSameDay(itemCalendar, yesterday)) {
                        yesterdayList.add(item)
                    }
                }

                onAction(
                    NotificationAction.LoadNotificationSuccess(
                        today = todayList,
                        yesterday = yesterdayList,
                        aya = "فَاذْكُرُونِي أَذْكُرْكُمْ وَاشْكُرُوا لِي وَلَا تَكْفُرُونِ"
                    )
                )


            }.onCompletion {
                onAction(NotificationAction.Loading(isLoading = false))
            }.catch { error ->
                onAction(NotificationAction.Error(error.message ?: "An unknown error occurred"))
            }.launchIn(viewModelScope)
    }

}


private fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
}
