package com.unsa.pokeayuda.ui.screens.settings

import com.unsa.pokeayuda.domain.model.CacheSize
import com.unsa.pokeayuda.domain.model.Theme

data class SettingsState(
    val generation: String = "generation-i",
    val theme: Theme = Theme.Default,
    val syncDays: Int = 7,
    val cacheSize: CacheSize = CacheSize(
        pokemon = 0,
        generation = 0,
        evolutionChain = 0,
        move = 0,
        ability = 0,
        type = 0
    ),
    val pokemonCacheSize: String = "0 B",
    val generationCacheSize: String = "0 B",
    val evolutionChainCacheSize: String = "0 B",
    val moveCacheSize: String = "0 B",
    val abilityCacheSize: String = "0 B",
    val typeCacheSize: String = "0 B",
    val totalCacheSize: String = "0 B",
    val isLoading: Boolean = true
)