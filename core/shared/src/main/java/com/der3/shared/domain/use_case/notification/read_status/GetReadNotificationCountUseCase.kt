package com.der3.shared.domain.use_case.notification.read_status

import com.der3.shared.domain.repo.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetReadNotificationCountUseCase {
    operator fun invoke(): Flow<Int>
}


class GetReadNotificationCountUseCaseImpl  @Inject constructor(
    private val repository: NotificationRepository
) : GetReadNotificationCountUseCase {
    override operator fun invoke(): Flow<Int> {
        return repository.getReadNotificationsCount()
    }
}