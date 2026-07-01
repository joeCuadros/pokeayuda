package com.unsa.pokeayuda.domain.usecase

import com.unsa.pokeayuda.domain.model.CacheSize

interface CacheManagementUseCase {
    suspend fun getPokemonCacheSize(): Long
    suspend fun clearPokemonCache()

    suspend fun getGenerationCacheSize(): Long
    suspend fun clearGenerationCache()

    suspend fun getEvolutionChainCacheSize(): Long
    suspend fun clearEvolutionChainCache()

    suspend fun getMoveCacheSize(): Long
    suspend fun clearMoveCache()

    suspend fun getAbilityCacheSize(): Long
    suspend fun clearAbilityCache()

    suspend fun getTypeCacheSize(): Long
    suspend fun clearTypeCache()

    suspend fun getTotalCacheSize(): CacheSize
    suspend fun clearAllCache()
}