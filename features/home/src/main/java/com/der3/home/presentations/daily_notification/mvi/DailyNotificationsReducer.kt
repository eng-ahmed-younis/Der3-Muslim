package com.der3.home.presentations.daily_notification.mvi

import com.der3.mvi.Reducer
import javax.inject.Inject


class DailyNotificationsReducer @Inject constructor() :
    Reducer<DailyNotificationsAction, DailyNotificationsState> {

    override fun reduce(
        action: DailyNotificationsAction,
        state: DailyNotificationsState
    ): DailyNotificationsState {
        TODO("Not yet implemented")
    }

}