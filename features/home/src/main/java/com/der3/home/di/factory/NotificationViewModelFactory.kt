package com.der3.home.di.factory

import com.der3.home.presentations.notification.NotificationViewModel
import com.der3.shared.params.NotificationParams
import dagger.assisted.AssistedFactory


@AssistedFactory
interface NotificationViewModelFactory {
    fun create(params: NotificationParams): NotificationViewModel
}