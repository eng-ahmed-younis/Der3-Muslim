package com.der3.shared.di.use_case

import com.der3.shared.domain.use_case.notification.*
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
}
