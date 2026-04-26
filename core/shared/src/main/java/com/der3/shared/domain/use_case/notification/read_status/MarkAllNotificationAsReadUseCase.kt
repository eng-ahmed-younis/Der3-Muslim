package com.der3.shared.domain.use_case.notification.read_status

import com.der3.shared.domain.repo.NotificationRepository
import javax.inject.Inject

interface MarkAllNotificationAsReadUseCase {
    suspend operator fun invoke()
}

class MarkAllNotificationAsReadUseCaseImpl  @Inject constructor(
    private val repository: NotificationRepository
) : MarkAllNotificationAsReadUseCase {
    override suspend operator fun invoke() {
        repository.markAllNotificationsAsRead()
    }
}