package com.unsa.pokeayuda.ui.screens.battle.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.unsa.pokeayuda.ui.screens.battle.PokemonStatRow

@Composable
fun TablaStatsBattleComponent(
    tablaStats: List<PokemonStatRow>,
    statOrdenadoPor: String?,
    statOrdenAscendente: Boolean,
    onOrdenarClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Extrae dinámicamente las columnas de estadísticas presentes en los datos reales
    val columnasStats = tablaStats.flatMap { it.stats.keys }.distinct()
    val scrollState = rememberScrollState()
    val anchoNombre = 110.dp
    val anchoStat = 65.dp

    if (tablaStats.isEmpty()) {
        Text(
            text = "No hay datos de equipo disponibles.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center
        )
        return
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.15f))
    ) {
        // CONTENEDOR PRINCIPAL CON SCROLL HORIZONTAL
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
                .padding(vertical = 8.dp)
        ) {
            Column {
                // 1. ENCABEZADO DE LA TABLA (Mapeado dinámicamente a columnasStats)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                ) {
                    Text(
                        text = "Pokémon",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .width(anchoNombre)
                            .padding(horizontal = 12.dp, vertical = 10.dp)
                    )

                    columnasStats.forEach { key ->
                        val esActivo = statOrdenadoPor == key
                        // Traduce los IDs de la PokeAPI a etiquetas cortas y legibles
                        val labelHeader = when (key) {
                            "hp" -> "HP"
                            "attack" -> "At"
                            "defense" -> "De"
                            "special-attack" -> "SA"
                            "special-defense" -> "SD"
                            "speed" -> "Spd"
                            "special" -> "Spe"
                            else -> key.uppercase()
                        }

                        Row(
                            modifier = Modifier
                                .width(anchoStat)
                                .clickable { onOrdenarClick(key) }
                                .padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = labelHeader,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = if (esActivo) FontWeight.Black else FontWeight.Bold,
                                color = if (esActivo) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            )
                            if (esActivo) {
                                Icon(
                                    imageVector = if (statOrdenAscendente) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(start = 2.dp)
                                )
                            }
                        }
                    }
                }

                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                // 2. FILAS DE LOS POKÉMON
                tablaStats.forEach { row ->
                    val esRival = row.esRival
                    val fondoFila = if (esRival) {
                        MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.35f)
                    } else {
                        MaterialTheme.colorScheme.surface
                    }

                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.background(fondoFila)
                        ) {
                            // Celda Nombre con formato condicional para el Rival
                            Box(
                                modifier = Modifier
                                    .width(anchoNombre)
                                    .padding(horizontal = 12.dp, vertical = 12.dp)
                            ) {
                                val nombreFormateado = row.nombre.replaceFirstChar { it.uppercase() }
                                Text(
                                    text = if (esRival) "$nombreFormateado (*)" else nombreFormateado,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = if (esRival) FontWeight.Bold else FontWeight.Medium,
                                    color = if (esRival) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1
                                )
                            }

                            // Celdas de Valores Numéricos correlacionadas al Header
                            columnasStats.forEach { keyActual ->
                                val valor = row.stats[keyActual] ?: 0
                                val esStatFiltroActivo = statOrdenadoPor == keyActual

                                Text(
                                    text = "$valor",
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Center,
                                    fontWeight = if (esStatFiltroActivo) FontWeight.Bold else FontWeight.Normal,
                                    color = if (esStatFiltroActivo) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier
                                        .width(anchoStat)
                                        .padding(vertical = 12.dp)
                                )
                            }
                        }
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                    }
                }
            }
        }
    }
}