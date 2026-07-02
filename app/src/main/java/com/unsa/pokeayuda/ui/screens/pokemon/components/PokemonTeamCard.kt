package com.unsa.pokeayuda.ui.screens.pokemon.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.unsa.pokeayuda.data.local.entity.EquipoPokemonEntity
import com.unsa.pokeayuda.data.remote.model.pokemon.PokemonGeneracionResult
import com.unsa.pokeayuda.utils.PokemonTypeStyles
import com.unsa.pokeayuda.utils.translations.StatTranslations
import com.unsa.pokeayuda.utils.translations.TypeTranslations

@Composable
fun PokemonTeamCard(
    entidad: EquipoPokemonEntity,
    detalle: PokemonGeneracionResult?,
    onEliminar: () -> Unit,
    onCardClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val nombre = detalle?.name?.replaceFirstChar { it.uppercase() } ?: "Cargando..."

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(260.dp)
            .clickable { expanded = !expanded },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        if (!expanded) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    if (detalle != null) {
                        AsyncImage(
                            model = detalle.sprites,
                            contentDescription = detalle.name,
                            modifier = Modifier
                                .size(100.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "$nombre - Gen ${entidad.idGeneracion}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (detalle != null) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        detalle.types.sortedBy { it.slot }.forEach { tipo ->
                            val style = PokemonTypeStyles.get(tipo.type.name)
                            Text(
                                text = style.displayName,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = style.textColor,
                                modifier = Modifier
                                    .border(
                                        width = 1.dp,
                                        color = style.borderColor,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .background(
                                        color = style.backgroundColor,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                AsyncImage(
                    model = detalle?.sprites,
                    contentDescription = detalle?.name,
                    modifier = Modifier.size(64.dp)
                )

                Text(
                    text = "$nombre - Gen ${entidad.idGeneracion}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )

                if (detalle != null) {
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        detalle.stats.forEach { statDto ->
                            Text(
                                text = "${StatTranslations.translate(statDto.stat.name)}: ${statDto.baseStat}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            detalle.types.sortedBy { it.slot }.forEach { tipo ->
                                val style = PokemonTypeStyles.get(tipo.type.name)
                                Text(
                                    text = style.displayName,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = style.textColor,
                                    modifier = Modifier
                                        .border(
                                            width = 1.dp,
                                            color = style.borderColor,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .background(
                                            color = style.backgroundColor,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Ver detalles",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.clickable { onCardClick(entidad.idPokemon) }
                        )
                        IconButton(
                            onClick = onEliminar,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Eliminar",
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}