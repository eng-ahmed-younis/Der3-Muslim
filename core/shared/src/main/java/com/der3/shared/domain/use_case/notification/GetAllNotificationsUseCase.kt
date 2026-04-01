package com.der3.shared.domain.use_case.notification

import com.der3.shared.data.source.local.entity.NotificationEntity
import com.der3.shared.domain.repo.NotificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface GetAllNotificationsUseCase {
    operator fun invoke(): Flow<List<NotificationEntity>>
}

class GetAllNotificationsUseCaseImpl @Inject constructor(
    private val repository: NotificationRepository
) : GetAllNotificationsUseCase {
    override fun invoke(): Flow<List<NotificationEntity>> =
        repository.getAllNotifications().flowOn(Dispatchers.IO)
}
