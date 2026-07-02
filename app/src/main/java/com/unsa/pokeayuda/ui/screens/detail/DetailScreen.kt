package com.unsa.pokeayuda.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.unsa.pokeayuda.ui.components.AppSection
import com.unsa.pokeayuda.ui.components.PokemonStatItem
import com.unsa.pokeayuda.ui.components.PokemonTypeChip
import com.unsa.pokeayuda.ui.screens.detail.components.CadenaEvolutivaComponent
import com.unsa.pokeayuda.ui.screens.detail.components.HabilidadesComponent
import com.unsa.pokeayuda.ui.screens.detail.components.MatrizTiposComponent
import com.unsa.pokeayuda.ui.screens.detail.components.TablaMovimientosYProgresoComponent
import com.unsa.pokeayuda.utils.translations.StatTranslations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen (
    pokemonId: Int,
    viewModel: DetailViewModel = hiltViewModel()
){
    LaunchedEffect(pokemonId) {
        viewModel.onEvent(DetailEvent.Inicializar(pokemonId))
    }

    val state = viewModel.state
    val pokemon = state.pokemonDetalle
    val nombrePokemon = pokemon?.name?.replaceFirstChar { it.uppercase() } ?: "Cargando..."

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "$nombrePokemon (#$pokemonId)",
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
            contentPadding = PaddingValues(16.dp)
        ) {
            if (state.isLoadingPokemon) {
                item {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            } else if (pokemon != null) {
                // [Imagen asincrono grande]
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = pokemon.sprites,
                            contentDescription = pokemon.name,
                            modifier = Modifier.size(200.dp)
                        )
                    }
                }
                // ---------- Seccion 1 ----------
                item {
                    AppSection(title = "Información Básica", icon = Icons.Default.Info) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                // Nombre y Generación
                                Text(
                                    text = "${nombrePokemon.replaceFirstChar { it.uppercase() }} — ${state.nombreGeneracionActual.uppercase()}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                // Chips de Tipos
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    pokemon.types.sortedBy { it.slot }.forEach { tipo ->
                                        PokemonTypeChip(type = tipo.type.name)
                                    }
                                }
                                HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
                                // Título de Sección Estadísticas
                                Text(
                                    text = "Estadísticas Base",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                // Calculo de promedio nativo
                                val promedio = pokemon.stats.map { it.baseStat }.average()
                                // Lista de Stats con el indicador integrado
                                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    pokemon.stats.forEach { stat ->
                                        PokemonStatItem(
                                            name = StatTranslations.translate(stat.stat.name),
                                            baseStat = stat.baseStat,
                                            effort = stat.effort,
                                            promedio = promedio
                                        )
                                    }
                                }
                                // Indicador del promedio general
                                HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Media global del Pokémon:",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = "%.1f pts".format(promedio),
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                }
                // ---------- Seccion 2 ----------
                item {
                    AppSection(title = "Movimientos por Nivel", icon = Icons.Default.List) {
                        if (state.ataquesVisibles.isEmpty() && !state.isLoadingAtaques) {
                            Button(onClick = { viewModel.onEvent(DetailEvent.ActivarAtaques) }) {
                                Text("Cargar Movimientos")
                            }
                        }
                        if (state.isLoadingAtaques) {
                            CircularProgressIndicator()
                        }
                        if (state.ataquesVisibles.isNotEmpty()) {
                            state.pokemonDetalle?.let { pokemon ->
                                TablaMovimientosYProgresoComponent(
                                    pokemon = pokemon,
                                    ataquesPorJuego = state.ataquesVisibles,
                                    evolucionDetalle = state.evolucionDetalle
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                }
                // ---------- Seccion 3 ----------
                item {
                    AppSection(title = "Habilidades", icon = Icons.Default.Star) {
                        if (state.habilidadesVisibles.isEmpty() && !state.isLoadingHabilidades) {
                            Button(onClick = { viewModel.onEvent(DetailEvent.ActivarHabilidades) }) {
                                Text("Cargar Habilidades")
                            }
                        }
                        if (state.isLoadingHabilidades) {
                            CircularProgressIndicator()
                        }
                        if (state.habilidadesVisibles.isNotEmpty()) {
                            HabilidadesComponent(habilidadesVisibles = state.habilidadesVisibles)
                        }
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                }

                // ---------- Seccion 4 ----------
                item {
                    AppSection(title = "Cadena Evolutiva", icon = Icons.Default.AccountTree) {
                        if (state.evolucionDetalle == null && !state.isLoadingEvoluciones) {
                            Button(onClick = { viewModel.onEvent(DetailEvent.ActivarCadenaEvolutiva) }) {
                                Text("Cargar Evoluciones")
                            }
                        }
                        if (state.isLoadingEvoluciones) {
                            CircularProgressIndicator()
                        }
                        state.evolucionDetalle?.let { detalleCadena ->
                            CadenaEvolutivaComponent(evolucionDetalle = detalleCadena)
                        }
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                }

                // ---------- Seccion 5 ----------
                item {
                    AppSection(title = "Debilidades y fortalezas", icon = Icons.Default.Shield) {
                        if (state.tiposVisibles.isEmpty() && !state.isLoadingTipos) {
                            Button(onClick = { viewModel.onEvent(DetailEvent.ActivarMatrizTipos) }) {
                                Text("Ver Debilidades y fortalezas")
                            }
                        }
                        if (state.isLoadingTipos) {
                            CircularProgressIndicator()
                        }
                        if (state.tiposVisibles.isNotEmpty()) {
                            MatrizTiposComponent(tiposVisibles = state.tiposVisibles)
                        }
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }
    }
}