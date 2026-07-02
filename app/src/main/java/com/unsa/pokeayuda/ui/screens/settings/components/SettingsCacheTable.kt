package com.unsa.pokeayuda.ui.screens.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unsa.pokeayuda.ui.screens.settings.SettingsState

@Composable
fun SettingsCacheTable(
    state: SettingsState,
    onDeletePokemon: () -> Unit,
    onDeleteGeneration: () -> Unit,
    onDeleteEvolutionChain: () -> Unit,
    onDeleteMove: () -> Unit,
    onDeleteAbility: () -> Unit,
    onDeleteType: () -> Unit,
    onDeleteAll: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            SettingsCacheRow(
                name = "Pokémon",
                size = state.pokemonCacheSize,
                onDelete = onDeletePokemon,
                icon = Icons.Default.Pets
            )
            SettingsCacheRow(
                name = "Generaciones",
                size = state.generationCacheSize,
                onDelete = onDeleteGeneration,
                icon = Icons.Default.Layers
            )
            SettingsCacheRow(
                name = "Cadena Evolutiva",
                size = state.evolutionChainCacheSize,
                onDelete = onDeleteEvolutionChain,
                icon = Icons.Default.Timeline
            )
            SettingsCacheRow(
                name = "Ataques",
                size = state.moveCacheSize,
                onDelete = onDeleteMove,
                icon = Icons.Default.FlashOn
            )
            SettingsCacheRow(
                name = "Habilidades",
                size = state.abilityCacheSize,
                onDelete = onDeleteAbility,
                icon = Icons.Default.Star
            )
            /*SettingsCacheRow(
                name = "Tipos",
                size = state.typeCacheSize,
                onDelete = onDeleteType,
                icon = Icons.Default.Category
            )*/

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )

            SettingsCacheRow(
                name = "Total",
                size = state.totalCacheSize,
                onDelete = onDeleteAll,
                icon = Icons.Default.DeleteSweep,
                highlighted = true
            )
        }
    }
}