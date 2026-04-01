package com.der3.shared.domain.use_case.notification

import com.der3.shared.domain.repo.NotificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface MarkNotificationAsReadUseCase {
    suspend operator fun invoke(id: Int)
}

class MarkNotificationAsReadUseCaseImpl @Inject constructor(
    private val repository: NotificationRepository
) : MarkNotificationAsReadUseCase {
    override suspend fun invoke(id: Int) = withContext(Dispatchers.IO) {
        repository.markAsRead(id)
    }
}
