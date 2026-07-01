package com.unsa.pokeayuda.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsa.pokeayuda.domain.repository.AppPreferencesRepository
import com.unsa.pokeayuda.domain.usecase.CacheManagementUseCase
import com.unsa.pokeayuda.utils.SizeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepository,
    private val cacheManagementUseCase: CacheManagementUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    init {
        observePreferences()
        viewModelScope.launch {
            refreshCacheSize()
        }
    }

    private fun observePreferences() {
        viewModelScope.launch {
            combine(
                appPreferencesRepository.generation,
                appPreferencesRepository.theme,
                appPreferencesRepository.syncDays
            ) { generation, theme, syncDays ->
                Triple(generation, theme, syncDays)
            }.collect { (generation, theme, syncDays) ->
                _state.update {
                    it.copy(
                        generation = generation,
                        theme = theme,
                        syncDays = syncDays,
                        isLoading = false
                    )
                }
            }
        }
    }

    private suspend fun refreshCacheSize() {
        val cache = cacheManagementUseCase.getTotalCacheSize()
        _state.update {
            it.copy(
                cacheSize = cache,
                pokemonCacheSize = SizeFormatter.format(cache.pokemon),
                generationCacheSize = SizeFormatter.format(cache.generation),
                evolutionChainCacheSize = SizeFormatter.format(cache.evolutionChain),
                moveCacheSize = SizeFormatter.format(cache.move),
                abilityCacheSize = SizeFormatter.format(cache.ability),
                typeCacheSize = SizeFormatter.format(cache.type),
                totalCacheSize = SizeFormatter.format(cache.total)
            )
        }
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.ChangeGeneration -> {
                viewModelScope.launch {
                    appPreferencesRepository.setGeneration(event.generation)
                }
            }
            is SettingsEvent.ChangeTheme -> {
                viewModelScope.launch {
                    appPreferencesRepository.setTheme(event.theme)
                }
            }
            is SettingsEvent.ChangeSyncDays -> {
                viewModelScope.launch {
                    appPreferencesRepository.setSyncDays(event.days)
                }
            }
            SettingsEvent.RefreshCacheSize -> {
                viewModelScope.launch {
                    refreshCacheSize()
                }
            }
            SettingsEvent.ClearPokemonCache -> {
                viewModelScope.launch {
                    cacheManagementUseCase.clearPokemonCache()
                    refreshCacheSize()
                }
            }
            SettingsEvent.ClearGenerationCache -> {
                viewModelScope.launch {
                    cacheManagementUseCase.clearGenerationCache()
                    refreshCacheSize()
                }
            }
            SettingsEvent.ClearEvolutionChainCache -> {
                viewModelScope.launch {
                    cacheManagementUseCase.clearEvolutionChainCache()
                    refreshCacheSize()
                }
            }
            SettingsEvent.ClearMoveCache -> {
                viewModelScope.launch {
                    cacheManagementUseCase.clearMoveCache()
                    refreshCacheSize()
                }
            }
            SettingsEvent.ClearAbilityCache -> {
                viewModelScope.launch {
                    cacheManagementUseCase.clearAbilityCache()
                    refreshCacheSize()
                }
            }
            SettingsEvent.ClearTypeCache -> {
                viewModelScope.launch {
                    cacheManagementUseCase.clearTypeCache()
                    refreshCacheSize()
                }
            }
            SettingsEvent.ClearAllCache -> {
                viewModelScope.launch {
                    cacheManagementUseCase.clearAllCache()
                    refreshCacheSize()
                }
            }
        }
    }
}