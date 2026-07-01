package com.unsa.pokeayuda.di

import com.unsa.pokeayuda.data.usecase.CacheManagementUseCaseImpl
import com.unsa.pokeayuda.domain.usecase.CacheManagementUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    @Singleton
    abstract fun bindCacheManagementUseCase(
        cacheManagementUseCaseImpl: CacheManagementUseCaseImpl
    ): CacheManagementUseCase
}