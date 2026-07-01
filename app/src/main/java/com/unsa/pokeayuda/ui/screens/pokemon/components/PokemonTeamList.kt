package com.unsa.pokeayuda.ui.screens.pokemon.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unsa.pokeayuda.data.local.entity.EquipoPokemonEntity
import com.unsa.pokeayuda.data.remote.model.pokemon.PokemonGeneracionResult

@Composable
fun PokemonTeamList(
    equipo: List<EquipoPokemonEntity>,
    detallesCargados: Map<Int, PokemonGeneracionResult>,
    onEliminarPokemon: (Int) -> Unit,
    onPokemonClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (equipo.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No tienes Pokémon en tu equipo",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    } else {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            equipo.forEach { entidad ->
                val detalle = detallesCargados[entidad.idPokemon]
                PokemonTeamCard(
                    entidad = entidad,
                    detalle = detalle,
                    onEliminar = { onEliminarPokemon(entidad.id) },
                    onCardClick = onPokemonClick
                )
            }
        }
    }
}