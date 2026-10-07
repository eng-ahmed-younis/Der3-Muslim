package com.der3.shared.domain.use_case

import app.cash.turbine.test
import com.der3.shared.data.source.local.entity.RecycleBinEntity
import com.der3.shared.domain.repo.RecycleBinRepository
import com.der3.shared.domain.use_case.recycler.ClearRecycleBinUseCaseImpl
import com.der3.shared.domain.use_case.recycler.GetRecycleBinItemsUseCaseImpl
import com.der3.shared.domain.use_case.recycler.InsertToRecycleBinUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class RecyclerUseCasesTest {

    private val repository: RecycleBinRepository = mockk()

    @Test
    fun `GetRecycleBinItemsUseCaseImpl returns deleted items flow`() = runTest {
        val useCase = GetRecycleBinItemsUseCaseImpl(repository)
        val item = RecycleBinEntity(
            id = 1,
            categoryId = 2,
            text = "محذوف",
            audioPath = "",
            repeatCount = 1,
            deletedAt = 1000L
        )
        every { repository.getAllDeletedItems() } returns flowOf(listOf(item))

        useCase.invoke().test {
            val list = awaitItem()
            assertEquals(1, list.size)
            assertEquals("محذوف", list[0].text)
            awaitComplete()
        }
    }

    @Test
    fun `InsertToRecycleBinUseCaseImpl invokes insertToRecycleBin on repository`() = runTest {
        val useCase = InsertToRecycleBinUseCaseImpl(repository)
        val item = RecycleBinEntity(
            id = 1,
            categoryId = 2,
            text = "محذوف",
            audioPath = "",
            repeatCount = 1,
            deletedAt = 1000L
        )
        coEvery { repository.insertToRecycleBin(item) } returns Unit

        useCase.invoke(item)

        coVerify(exactly = 1) { repository.insertToRecycleBin(item) }
    }

    @Test
    fun `ClearRecycleBinUseCaseImpl invokes clearRecycleBin on repository`() = runTest {
        val useCase = ClearRecycleBinUseCaseImpl(repository)
        coEvery { repository.clearRecycleBin() } returns Unit

        useCase.invoke()

        coVerify(exactly = 1) { repository.clearRecycleBin() }
    }
}
