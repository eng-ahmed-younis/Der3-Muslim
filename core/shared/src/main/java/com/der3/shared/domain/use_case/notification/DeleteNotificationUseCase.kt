package com.der3.shared.domain.use_case.notification

import com.der3.shared.domain.repo.NotificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface DeleteNotificationUseCase {
    suspend operator fun invoke(id: String)
}

class DeleteNotificationUseCaseImpl @Inject constructor(
    private val repository: NotificationRepository
) : DeleteNotificationUseCase {
    override suspend fun invoke(id: String) = withContext(Dispatchers.IO) {
        repository.deleteNotificationById(id)
    }
}
