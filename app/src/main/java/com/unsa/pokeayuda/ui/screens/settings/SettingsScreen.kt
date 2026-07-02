package com.unsa.pokeayuda.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unsa.pokeayuda.ui.components.AppDeleteConfirmationDialog
import com.unsa.pokeayuda.ui.components.AppSection
import com.unsa.pokeayuda.ui.screens.settings.components.SettingsCacheTable
import com.unsa.pokeayuda.ui.screens.settings.components.SettingsCredits
import com.unsa.pokeayuda.ui.screens.settings.components.SettingsPersonalization

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var showDialog by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }
    var dialogMessage by remember { mutableStateOf("") }
    var deleteAction by remember { mutableStateOf<(() -> Unit)?>(null) }

    fun requestDelete(title: String, message: String, action: () -> Unit) {
        dialogTitle = title
        dialogMessage = message
        deleteAction = action
        showDialog = true
    }

    if (showDialog) {
        AppDeleteConfirmationDialog(
            title = dialogTitle,
            message = dialogMessage,
            onConfirm = {
                deleteAction?.invoke()
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Ajustes",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding()),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            item {
                AppSection(title = "Personalizar", icon = Icons.Default.Tune) {
                    SettingsPersonalization(
                        generation = state.generation,
                        theme = state.theme,
                        syncDays = state.syncDays,
                        onGenerationChange = {
                            viewModel.onEvent(SettingsEvent.ChangeGeneration(it))
                        },
                        onThemeChange = {
                            viewModel.onEvent(SettingsEvent.ChangeTheme(it))
                        },
                        onSyncDaysChange = {
                            viewModel.onEvent(SettingsEvent.ChangeSyncDays(it))
                        }
                    )
                }
            }
            item {
                AppSection(title = "Datos", icon = Icons.Default.Storage) {
                    SettingsCacheTable(
                        state = state,
                        onDeletePokemon = {
                            requestDelete(
                                title = "Eliminar caché Pokémon",
                                message = "Se liberará ${state.pokemonCacheSize}. Esta acción no se puede deshacer."
                            ) { viewModel.onEvent(SettingsEvent.ClearPokemonCache) }
                        },
                        onDeleteGeneration = {
                            requestDelete(
                                title = "Eliminar caché Generaciones",
                                message = "Se liberará ${state.generationCacheSize}. Esta acción no se puede deshacer."
                            ) { viewModel.onEvent(SettingsEvent.ClearGenerationCache) }
                        },
                        onDeleteEvolutionChain = {
                            requestDelete(
                                title = "Eliminar caché Cadena Evolutiva",
                                message = "Se liberará ${state.evolutionChainCacheSize}. Esta acción no se puede deshacer."
                            ) { viewModel.onEvent(SettingsEvent.ClearEvolutionChainCache) }
                        },
                        onDeleteMove = {
                            requestDelete(
                                title = "Eliminar caché Ataques",
                                message = "Se liberará ${state.moveCacheSize}. Esta acción no se puede deshacer."
                            ) { viewModel.onEvent(SettingsEvent.ClearMoveCache) }
                        },
                        onDeleteAbility = {
                            requestDelete(
                                title = "Eliminar caché Habilidades",
                                message = "Se liberará ${state.abilityCacheSize}. Esta acción no se puede deshacer."
                            ) { viewModel.onEvent(SettingsEvent.ClearAbilityCache) }
                        },
                        onDeleteType = {
                            requestDelete(
                                title = "Eliminar caché Tipos",
                                message = "Se liberará ${state.typeCacheSize}. Esta acción no se puede deshacer."
                            ) { viewModel.onEvent(SettingsEvent.ClearTypeCache) }
                        },
                        onDeleteAll = {
                            requestDelete(
                                title = "Eliminar toda la caché",
                                message = "Se liberará ${state.totalCacheSize}. Esta acción no se puede deshacer."
                            ) { viewModel.onEvent(SettingsEvent.ClearAllCache) }
                        }
                    )
                }
            }
            item {
                AppSection(title = "Créditos", icon = Icons.Default.Info) {
                    SettingsCredits()
                }
            }
        }
    }
}
