package com.der3.shared.domain.use_case

import app.cash.turbine.test
import com.der3.shared.data.source.local.entity.NotificationEntity
import com.der3.shared.domain.repo.NotificationRepository
import com.der3.shared.domain.use_case.notification.ClearAllNotificationsUseCaseImpl
import com.der3.shared.domain.use_case.notification.DeleteNotificationUseCaseImpl
import com.der3.shared.domain.use_case.notification.GetAllNotificationsUseCaseImpl
import com.der3.shared.domain.use_case.notification.InsertNotificationUseCaseImpl
import com.der3.shared.domain.use_case.notification.MarkNotificationAsReadUseCaseImpl
import com.der3.shared.domain.use_case.notification.read_status.GetUnReadNotificationCountUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class NotificationUseCasesTest {

    private val repository: NotificationRepository = mockk()

    @Test
    fun `GetAllNotificationsUseCaseImpl returns notifications flow`() = runTest {
        val useCase = GetAllNotificationsUseCaseImpl(repository)
        val entity = NotificationEntity(id = "1", title = "عنوان", body = "محتوى")
        every { repository.getAllNotifications() } returns flowOf(listOf(entity))

        useCase.invoke().test {
            val list = awaitItem()
            assertEquals(1, list.size)
            assertEquals("عنوان", list[0].title)
            awaitComplete()
        }
    }

    @Test
    fun `GetUnReadNotificationCountUseCaseImpl returns unread count flow`() = runTest {
        val useCase = GetUnReadNotificationCountUseCaseImpl(repository)
        every { repository.getUnreadNotificationsCount() } returns flowOf(5)

        useCase.invoke().test {
            assertEquals(5, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `InsertNotificationUseCaseImpl invokes insertNotification on repository`() = runTest {
        val useCase = InsertNotificationUseCaseImpl(repository)
        val entity = NotificationEntity(id = "1", title = "عنوان", body = "محتوى")
        coEvery { repository.insertNotification(entity) } returns Unit

        useCase.invoke(entity)

        coVerify(exactly = 1) { repository.insertNotification(entity) }
    }

    @Test
    fun `DeleteNotificationUseCaseImpl invokes deleteNotificationById on repository`() = runTest {
        val useCase = DeleteNotificationUseCaseImpl(repository)
        coEvery { repository.deleteNotificationById("1") } returns Unit

        useCase.invoke("1")

        coVerify(exactly = 1) { repository.deleteNotificationById("1") }
    }

    @Test
    fun `ClearAllNotificationsUseCaseImpl invokes deleteAllNotifications on repository`() = runTest {
        val useCase = ClearAllNotificationsUseCaseImpl(repository)
        coEvery { repository.deleteAllNotifications() } returns Unit

        useCase.invoke()

        coVerify(exactly = 1) { repository.deleteAllNotifications() }
    }
}
