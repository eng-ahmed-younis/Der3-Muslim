package com.der3.shared.domain.use_case.notification

import com.der3.shared.data.source.local.entity.NotificationEntity
import com.der3.shared.domain.repo.NotificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface InsertNotificationUseCase {
    suspend operator fun invoke(notification: NotificationEntity)
}

class InsertNotificationUseCaseImpl @Inject constructor(
    private val repository: NotificationRepository
) : InsertNotificationUseCase {
    override suspend fun invoke(notification: NotificationEntity) = withContext(Dispatchers.IO) {
        repository.insertNotification(notification)
    }
}
