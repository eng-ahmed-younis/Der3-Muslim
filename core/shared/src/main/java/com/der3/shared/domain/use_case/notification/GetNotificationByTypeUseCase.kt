package com.der3.shared.domain.use_case.notification

import com.der3.shared.data.source.local.entity.NotificationEntity
import com.der3.shared.domain.repo.NotificationRepository
import com.der3.model.NotificationType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetNotificationByTypeUseCase {
    operator fun invoke(type: NotificationType): Flow<NotificationEntity?>
}

class GetNotificationByTypeUseCaseImpl @Inject constructor(
    private val repository: NotificationRepository
) : GetNotificationByTypeUseCase {
    override fun invoke(type: NotificationType): Flow<NotificationEntity?> =
        repository.getNotificationByType(type.value)
}
