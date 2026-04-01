package com.der3.shared.domain.use_case.notification

import com.der3.shared.domain.repo.NotificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface ClearNotificationsByTypeUseCase {
    suspend operator fun invoke(type: String)
}

class ClearNotificationsByTypeUseCaseImpl @Inject constructor(
    private val repository: NotificationRepository
) : ClearNotificationsByTypeUseCase {

    override suspend fun invoke(type: String) = withContext(Dispatchers.IO) {
        repository.deleteNotificationsByType(type)
    }
}
