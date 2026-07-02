package com.unsa.pokeayuda.ui.screens.battle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.unsa.pokeayuda.ui.components.AppSection
import com.unsa.pokeayuda.ui.components.PokemonSelector
import com.unsa.pokeayuda.ui.components.PokemonTypeChip
import com.unsa.pokeayuda.ui.screens.battle.components.TablaStatsBattleComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BattleScreen(
    viewModel: BattleViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onEvent(BattleEvent.Inicializar)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Análisis de Combate",
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                AppSection(title = "Seleccionar un Pokémon a combatir", icon = Icons.Default.Search) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Implementación de tu componente reutilizable
                        PokemonSelector(
                            busquedaTexto = state.busquedaRivalTexto,
                            nombresPokemonFiltrados = state.nombresPokemonFiltrados,
                            onBusquedaCambiada = { query ->
                                viewModel.onEvent(BattleEvent.CambiarBusquedaRival(query))
                            },
                            onPokemonSeleccionado = { nombre ->
                                viewModel.onEvent(BattleEvent.SeleccionarRival(nombre))
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Indicador de carga mientras descarga los datos del Pokémon rival
                        if (state.isLoadingRival) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        // Detalle visual del Pokémon oponente seleccionado
                        state.rivalPokemon?.let { rival ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                                ),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    AsyncImage(
                                        model = rival.sprites,
                                        contentDescription = rival.name,
                                        modifier = Modifier
                                            .size(90.dp)
                                            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
                                            .padding(6.dp),
                                        contentScale = ContentScale.Fit
                                    )

                                    Column(
                                        modifier = Modifier.weight(1f),
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text(
                                            text = rival.name,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = state.nombreGeneracionActual,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                                            modifier = Modifier.padding(top = 4.dp)
                                        ) {
                                            rival.types.forEach { typeSlot ->
                                                PokemonTypeChip(type = typeSlot.type.name)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            item {
                AppSection(title = "Tabla de stats", icon = Icons.Default.Info) {
                    TablaStatsBattleComponent(
                        tablaStats = state.tablaStats,
                        statOrdenadoPor = state.statOrdenadoPor,
                        statOrdenAscendente = state.statOrdenAscendente,
                        onOrdenarClick = { statKey ->
                            viewModel.onEvent(BattleEvent.OrdenarPorStat(statKey))
                        },
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                    )
                }
            }
            item {
                AppSection(title = "Tabla de debilidades", icon = Icons.Default.Info) {

                }
            }
        }
    }
}