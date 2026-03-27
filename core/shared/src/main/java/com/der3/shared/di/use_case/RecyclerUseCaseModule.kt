package com.der3.shared.di.use_case

import com.der3.shared.domain.repo.RecycleBinRepository
import com.der3.shared.domain.use_case.recycler.ClearRecycleBinUseCase
import com.der3.shared.domain.use_case.recycler.ClearRecycleBinUseCaseImpl
import com.der3.shared.domain.use_case.recycler.DeleteRecyclerItemByIdUseCase
import com.der3.shared.domain.use_case.recycler.DeleteRecyclerItemByIdUseCaseImpl
import com.der3.shared.domain.use_case.recycler.GetRecycleBinItemsUseCase
import com.der3.shared.domain.use_case.recycler.GetRecycleBinItemsUseCaseImpl
import com.der3.shared.domain.use_case.recycler.InsertAllToRecycleBinUseCase
import com.der3.shared.domain.use_case.recycler.InsertAllToRecycleBinUseCaseImpl
import com.der3.shared.domain.use_case.recycler.InsertToRecycleBinUseCase
import com.der3.shared.domain.use_case.recycler.InsertToRecycleBinUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RecyclerUseCaseModule {

    @Provides
    @Singleton
    fun provideGetRecycleBinItems(
        repository: RecycleBinRepository
    ): GetRecycleBinItemsUseCase = GetRecycleBinItemsUseCaseImpl(repository = repository)


    @Provides
    @Singleton
    fun provideInsertToRecycleBinUseCase(
        repository: RecycleBinRepository
    ): InsertToRecycleBinUseCase = InsertToRecycleBinUseCaseImpl(repository = repository)

    @Provides
    @Singleton
    fun provideInsertAllToRecycleBinUseCase(
        repository: RecycleBinRepository
    ): InsertAllToRecycleBinUseCase = InsertAllToRecycleBinUseCaseImpl(repository = repository)

    @Provides
    @Singleton
    fun provideDeleteRecyclerItemByIdUseCase(
        repository: RecycleBinRepository
    ): DeleteRecyclerItemByIdUseCase = DeleteRecyclerItemByIdUseCaseImpl(repository = repository)

    @Provides
    @Singleton
    fun provideClearRecycleBinUseCase(
        repository: RecycleBinRepository
    ): ClearRecycleBinUseCase = ClearRecycleBinUseCaseImpl(repository = repository)
}
