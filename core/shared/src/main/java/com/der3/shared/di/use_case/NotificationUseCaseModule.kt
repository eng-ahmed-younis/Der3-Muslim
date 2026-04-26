package com.der3.shared.di.use_case

import com.der3.shared.domain.use_case.notification.ClearAllNotificationsUseCase
import com.der3.shared.domain.use_case.notification.ClearAllNotificationsUseCaseImpl
import com.der3.shared.domain.use_case.notification.ClearNotificationsByTypeUseCase
import com.der3.shared.domain.use_case.notification.ClearNotificationsByTypeUseCaseImpl
import com.der3.shared.domain.use_case.notification.DeleteNotificationUseCase
import com.der3.shared.domain.use_case.notification.DeleteNotificationUseCaseImpl
import com.der3.shared.domain.use_case.notification.GetAllNotificationsUseCase
import com.der3.shared.domain.use_case.notification.GetAllNotificationsUseCaseImpl
import com.der3.shared.domain.use_case.notification.GetNotificationByTypeUseCase
import com.der3.shared.domain.use_case.notification.GetNotificationByTypeUseCaseImpl
import com.der3.shared.domain.use_case.notification.InsertNotificationUseCase
import com.der3.shared.domain.use_case.notification.InsertNotificationUseCaseImpl
import com.der3.shared.domain.use_case.notification.MarkNotificationAsReadUseCase
import com.der3.shared.domain.use_case.notification.MarkNotificationAsReadUseCaseImpl
import com.der3.shared.domain.use_case.notification.read_status.GetReadNotificationCountUseCase
import com.der3.shared.domain.use_case.notification.read_status.GetReadNotificationCountUseCaseImpl
import com.der3.shared.domain.use_case.notification.read_status.GetUnReadNotificationCountUseCase
import com.der3.shared.domain.use_case.notification.read_status.GetUnReadNotificationCountUseCaseImpl
import com.der3.shared.domain.use_case.notification.read_status.MarkAllNotificationAsReadUseCase
import com.der3.shared.domain.use_case.notification.read_status.MarkAllNotificationAsReadUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationUseCaseModule {

    @Binds
    @Singleton
    abstract fun bindGetAllNotificationsUseCase(
        impl: GetAllNotificationsUseCaseImpl
    ): GetAllNotificationsUseCase

    @Binds
    @Singleton
    abstract fun bindInsertNotificationUseCase(
        impl: InsertNotificationUseCaseImpl
    ): InsertNotificationUseCase

    @Binds
    @Singleton
    abstract fun bindDeleteNotificationUseCase(
        impl: DeleteNotificationUseCaseImpl
    ): DeleteNotificationUseCase

    @Binds
    @Singleton
    abstract fun bindMarkNotificationAsReadUseCase(
        impl: MarkNotificationAsReadUseCaseImpl
    ): MarkNotificationAsReadUseCase

    @Binds
    @Singleton
    abstract fun bindClearAllNotificationsUseCase(
        impl: ClearAllNotificationsUseCaseImpl
    ): ClearAllNotificationsUseCase

    @Binds
    @Singleton
    abstract fun bindGetNotificationByTypeUseCase(
        impl: GetNotificationByTypeUseCaseImpl
    ): GetNotificationByTypeUseCase

    @Binds
    @Singleton
    abstract fun bindClearNotificationsByTypeUseCase(
        impl: ClearNotificationsByTypeUseCaseImpl
    ): ClearNotificationsByTypeUseCase

    @Binds
    @Singleton
    abstract fun bindGetReadNotificationCountUseCase(
        impl: GetReadNotificationCountUseCaseImpl
    ): GetReadNotificationCountUseCase

    @Binds
    @Singleton
    abstract fun bindGetUnReadNotificationCountUseCase(
        impl: GetUnReadNotificationCountUseCaseImpl
    ): GetUnReadNotificationCountUseCase


    @Binds
    @Singleton
    abstract fun bindMarkAllNotificationAsReadUseCase(
        impl: MarkAllNotificationAsReadUseCaseImpl
    ): MarkAllNotificationAsReadUseCase
}
