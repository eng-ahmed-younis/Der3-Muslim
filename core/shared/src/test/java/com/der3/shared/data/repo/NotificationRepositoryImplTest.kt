package com.der3.shared.data.repo

import app.cash.turbine.test
import com.der3.shared.data.source.local.dao.NotificationDao
import com.der3.shared.data.source.local.entity.NotificationEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NotificationRepositoryImplTest {

    private val notificationDao: NotificationDao = mockk()
    private lateinit var repository: NotificationRepositoryImpl

    @Before
    fun setUp() {
        repository = NotificationRepositoryImpl(notificationDao)
    }

    @Test
    fun `getAllNotifications returns flow from dao`() = runTest {
        val entity = NotificationEntity(id = "1", title = "تنبيه", body = "محتوى التنبيه")
        every { notificationDao.getAllNotifications() } returns flowOf(listOf(entity))

        repository.getAllNotifications().test {
            val list = awaitItem()
            assertEquals(1, list.size)
            assertEquals("تنبيه", list[0].title)
            awaitComplete()
        }
    }

    @Test
    fun `insertNotification calls insertNotification on dao`() = runTest {
        val entity = NotificationEntity(id = "1", title = "تنبيه", body = "محتوى التنبيه")
        coEvery { notificationDao.insertNotification(entity) } returns Unit

        repository.insertNotification(entity)

        coVerify(exactly = 1) { notificationDao.insertNotification(entity) }
    }

    @Test
    fun `markAsRead calls markAsRead on dao`() = runTest {
        coEvery { notificationDao.markAsRead("1") } returns Unit

        repository.markAsRead("1")

        coVerify(exactly = 1) { notificationDao.markAsRead("1") }
    }

    @Test
    fun `getUnreadNotificationsCount returns count flow from dao`() = runTest {
        every { notificationDao.getUnreadNotificationsCount() } returns flowOf(3)

        repository.getUnreadNotificationsCount().test {
            assertEquals(3, awaitItem())
            awaitComplete()
        }
    }
}
