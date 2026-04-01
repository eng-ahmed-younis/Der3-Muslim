package com.der3.shared.domain.use_case.notification

import com.der3.shared.domain.repo.NotificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface ClearAllNotificationsUseCase {
    suspend operator fun invoke()
}

class ClearAllNotificationsUseCaseImpl @Inject constructor(
    private val repository: NotificationRepository
) : ClearAllNotificationsUseCase {
    override suspend fun invoke() = withContext(Dispatchers.IO) {
        repository.deleteAllNotifications()
    }
}
