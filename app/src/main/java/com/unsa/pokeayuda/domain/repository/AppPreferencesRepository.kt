package com.unsa.pokeayuda.domain.repository

import com.unsa.pokeayuda.domain.model.Theme
import kotlinx.coroutines.flow.Flow

interface AppPreferencesRepository {
    val generation: Flow<String>
    val theme: Flow<Theme>
    val syncDays: Flow<Int>
    suspend fun setGeneration(generation: String)
    suspend fun setTheme(theme: Theme)
    suspend fun setSyncDays(days: Int)
}