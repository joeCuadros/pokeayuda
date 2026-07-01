package com.unsa.pokeayuda.ui.screens.settings

import com.unsa.pokeayuda.domain.model.Theme

sealed interface SettingsEvent {
    data class ChangeGeneration(
        val generation: String
    ) : SettingsEvent
    data class ChangeTheme(
        val theme: Theme
    ) : SettingsEvent
    data class ChangeSyncDays(
        val days: Int
    ) : SettingsEvent
    data object RefreshCacheSize : SettingsEvent
    data object ClearPokemonCache : SettingsEvent
    data object ClearGenerationCache : SettingsEvent
    data object ClearEvolutionChainCache : SettingsEvent
    data object ClearMoveCache : SettingsEvent
    data object ClearAbilityCache : SettingsEvent
    data object ClearTypeCache : SettingsEvent
    data object ClearAllCache : SettingsEvent
}