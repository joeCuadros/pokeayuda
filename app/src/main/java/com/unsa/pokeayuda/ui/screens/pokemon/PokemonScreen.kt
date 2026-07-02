package com.unsa.pokeayuda.ui.screens.pokemon

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CatchingPokemon
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unsa.pokeayuda.ui.components.AppSection
import com.unsa.pokeayuda.ui.components.PokemonSelector
import com.unsa.pokeayuda.ui.screens.pokemon.components.PokemonTeamList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: PokemonViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Mis Pokémon",
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
            item {
                AppSection(title = "Agregar al Equipo", icon = Icons.Default.Add) {
                    PokemonSelector(
                        busquedaTexto = state.busquedaTexto,
                        nombresPokemonFiltrados = state.nombresPokemonFiltrados,
                        onBusquedaCambiada = {
                            viewModel.onEvent(PokemonEvent.CambiarBusqueda(it))
                        },
                        onPokemonSeleccionado = { nombreSeleccionado ->
                            val index = state.nombresPokemonDisponibles.indexOf(nombreSeleccionado)
                            if (index != -1) {
                                viewModel.onEvent(PokemonEvent.AgregarAlEquipo(index + 1))
                            }
                        }
                    )
                }
            }

            item {
                AppSection(title = "Mi Equipo Actual", icon = Icons.Default.CatchingPokemon) {
                    PokemonTeamList(
                        equipo = state.equipoActual,
                        detallesCargados = state.detallesPokemonCargados,
                        onEliminarPokemon = { idFilaRoom ->
                            viewModel.onEvent(PokemonEvent.EliminarDelEquipo(idFilaRoom))
                        },
                        onPokemonClick = onNavigateToDetail
                    )
                }
            }
        }
    }
}