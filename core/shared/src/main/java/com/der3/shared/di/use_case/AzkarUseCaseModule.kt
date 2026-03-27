package com.der3.shared.di.use_case

import com.der3.shared.domain.use_case.GetAzkarCategoriesUseCase
import com.der3.shared.domain.use_case.GetAzkarCategoriesUseCaseImpl
import com.der3.shared.domain.use_case.GetAzkarCategoryByIdUseCase
import com.der3.shared.domain.use_case.GetAzkarCategoryByIdUseCaseImpl
import com.der3.shared.domain.use_case.GetAzkarItemByIdUseCase
import com.der3.shared.domain.use_case.GetAzkarItemByIdUseCaseImpl
import com.der3.shared.domain.use_case.GetAzkarItemsByCountUseCase
import com.der3.shared.domain.use_case.GetAzkarItemsByCountUseCaseImpl
import com.der3.shared.domain.use_case.GetTotalAzkarCountUseCase
import com.der3.shared.domain.use_case.GetTotalAzkarCountUseCaseImpl
import com.der3.shared.domain.use_case.SearchAzkarAllUseCase
import com.der3.shared.domain.use_case.SearchAzkarAllUseCaseImpl
import com.der3.shared.domain.use_case.SearchAzkarCategoriesUseCase
import com.der3.shared.domain.use_case.SearchAzkarCategoriesUseCaseImpl
import com.der3.shared.domain.use_case.SearchAzkarItemsUseCase
import com.der3.shared.domain.use_case.SearchAzkarItemsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AzkarUseCaseModule {

    @Binds
    abstract fun bindGetCategories(impl: GetAzkarCategoriesUseCaseImpl): GetAzkarCategoriesUseCase

    @Binds
    abstract fun bindGetCategoryById(impl: GetAzkarCategoryByIdUseCaseImpl): GetAzkarCategoryByIdUseCase

    @Binds
    abstract fun bindSearchCategories(impl: SearchAzkarCategoriesUseCaseImpl): SearchAzkarCategoriesUseCase

    @Binds
    abstract fun bindSearchItems(impl: SearchAzkarItemsUseCaseImpl): SearchAzkarItemsUseCase

    @Binds
    abstract fun bindSearchAll(impl: SearchAzkarAllUseCaseImpl): SearchAzkarAllUseCase

    @Binds
    abstract fun bindGetItemById(impl: GetAzkarItemByIdUseCaseImpl): GetAzkarItemByIdUseCase

    @Binds
    abstract fun bindGetItemsByCount(impl: GetAzkarItemsByCountUseCaseImpl): GetAzkarItemsByCountUseCase

    @Binds
    abstract fun bindGetTotalCount(impl: GetTotalAzkarCountUseCaseImpl): GetTotalAzkarCountUseCase
}