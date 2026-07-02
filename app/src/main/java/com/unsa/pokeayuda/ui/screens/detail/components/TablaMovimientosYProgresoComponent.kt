package com.unsa.pokeayuda.ui.screens.detail.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.unsa.pokeayuda.data.remote.model.evolution.EvolutionNodeResult
import com.unsa.pokeayuda.data.remote.model.move.MoveGeneracionResult
import com.unsa.pokeayuda.data.remote.model.pokemon.PokemonGeneracionResult
import com.unsa.pokeayuda.utils.translations.MoveAilmentTranslations
import com.unsa.pokeayuda.utils.translations.MoveCategoryTranslations
import com.unsa.pokeayuda.utils.translations.MoveDamageClassTranslations
import com.unsa.pokeayuda.utils.translations.MoveTargetTranslations
import com.unsa.pokeayuda.utils.translations.StatTranslations
import com.unsa.pokeayuda.utils.translations.TypeTranslations

sealed class TablaProgresoItem(val nivel: Int) {
    class Movimiento(nivel: Int, val info: MoveGeneracionResult) : TablaProgresoItem(nivel)
    class Evolucion(nivel: Int, val nombreEvolucion: String) : TablaProgresoItem(nivel)
}

@Composable
fun TablaMovimientosYProgresoComponent(
    pokemon: PokemonGeneracionResult,
    ataquesPorJuego: Map<String, Map<Int, List<MoveGeneracionResult>>>,
    evolucionDetalle: EvolutionNodeResult?,
    modifier: Modifier = Modifier
) {
    val listaJuegos = remember(ataquesPorJuego) { ataquesPorJuego.keys.toList() }
    var juegoSeleccionado by remember(ataquesPorJuego) { mutableStateOf(listaJuegos.firstOrNull() ?: "") }

    val listaTablaCronologica = remember(ataquesPorJuego, juegoSeleccionado, evolucionDetalle) {
        val listaItems = mutableListOf<TablaProgresoItem>()
        val ataquesDelJuegoActual = ataquesPorJuego[juegoSeleccionado] ?: emptyMap()

        ataquesDelJuegoActual.forEach { (nivel, listaAtaques) ->
            listaAtaques.forEach { ataque ->
                listaItems.add(TablaProgresoItem.Movimiento(nivel, ataque))
            }
        }

        var nodoActual = evolucionDetalle
        while (nodoActual != null) {
            val siguienteNodo = nodoActual.evolves_to.firstOrNull()
            if (siguienteNodo != null) {
                val detalleEvolucion = siguienteNodo.evolution_details.firstOrNull()
                if (detalleEvolucion?.method == "level-up" && detalleEvolucion.level != null) {
                    listaItems.add(TablaProgresoItem.Evolucion(detalleEvolucion.level, siguienteNodo.name))
                }
            }
            nodoActual = siguienteNodo
        }

        listaItems.sortedWith(compareBy({ it.nivel }, { it is TablaProgresoItem.Evolucion }))
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listaJuegos.forEach { juegoKey ->
                val esActivo = juegoKey == juegoSeleccionado
                val contenedorColor = if (esActivo) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                val textoColor = if (esActivo) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                val bordeColor = if (esActivo) Color.Transparent else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)

                Box(
                    modifier = Modifier
                        .border(1.dp, bordeColor, RoundedCornerShape(20.dp))
                        .clip(RoundedCornerShape(20.dp))
                        .background(contenedorColor)
                        .clickable { juegoSeleccionado = juegoKey }
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = juegoKey.uppercase().replace("-", " "),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = if (esActivo) FontWeight.Bold else FontWeight.Medium,
                        color = textoColor
                    )
                }
            }
        }

        if (listaTablaCronologica.isEmpty()) {
            Text(
                text = "No hay registros de crecimiento para esta edición.",
                style = MaterialTheme.typography.bodyMedium,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(vertical = 12.dp)
            )
        } else {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp, bottomStart = 0.dp, bottomEnd = 0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Niv.", Modifier.weight(1.2f), style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                    Text("Movimiento / Evento", Modifier.weight(3.5f), style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                    Text("Pow", Modifier.weight(1f), style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                    Text("Acc", Modifier.weight(1f), style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                    Text("PP", Modifier.weight(1f), style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                    Spacer(Modifier.width(24.dp))
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                listaTablaCronologica.forEach { item ->
                    when (item) {
                        is TablaProgresoItem.Movimiento -> {
                            FilaTablaMovimiento(nivel = item.nivel, movimiento = item.info)
                        }
                        is TablaProgresoItem.Evolucion -> {
                            FilaTablaEvolucion(nivel = item.nivel, nombreEvolucion = item.nombreEvolucion)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FilaTablaMovimiento(nivel: Int, movimiento: MoveGeneracionResult) {
    var expandido by remember { mutableStateOf(false) }

    val descripcionPokedex = remember(movimiento.nameFlavorText) {
        movimiento.nameFlavorText.randomOrNull()?.replace("\n", " ") ?: "Sin registros de descripción en la base de datos."
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f), RoundedCornerShape(4.dp))
            .background(if (expandido) MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surface)
            .clickable { expandido = !expandido }
            .padding(vertical = 10.dp, horizontal = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "$nivel", modifier = Modifier.weight(1.2f), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.primary)
            Text(text = movimiento.nameEs, modifier = Modifier.weight(3.5f), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
            Text(text = movimiento.power?.toString() ?: "—", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
            Text(text = movimiento.accuracy?.let { "$it%" } ?: "—", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
            Text(text = movimiento.pp?.toString() ?: "—", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)

            Icon(
                imageVector = if (expandido) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        AnimatedVisibility(visible = expandido) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f), RoundedCornerShape(6.dp))
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "\"$descripcionPokedex\"",
                    style = MaterialTheme.typography.bodySmall,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.06f))

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    InfoTag(label = "Tipo", value = TypeTranslations.translate(movimiento.type))
                    InfoTag(label = "Clase", value = MoveDamageClassTranslations.translate(movimiento.damageClass))
                    InfoTag(label = "Categoría", value = MoveCategoryTranslations.translate(movimiento.meta.category))
                    InfoTag(label = "Objetivo", value = MoveTargetTranslations.translate(movimiento.target))
                    InfoTag(label = "Prioridad", value = if (movimiento.priority > 0) "+${movimiento.priority}" else "${movimiento.priority}")
                }

                movimiento.effect?.let { textEff ->
                    val factorProbabilidad = movimiento.effectChance ?: movimiento.meta.statChance
                    val txtLimpio = textEff.replace("\$effect_chance", factorProbabilidad.toString())
                    Text(
                        text = "Efecto: $txtLimpio",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                }

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val meta = movimiento.meta

                    if (meta.healing != 0) {
                        MetaChip(text = "Curación: ${meta.healing}% PS", color = Color(0xFFE8F5E9), textColor = Color(0xFF2E7D32))
                    }
                    if (meta.ailment != "none") {
                        val ailmentStr = MoveAilmentTranslations.translate(meta.ailment)
                        MetaChip(text = "Estado: $ailmentStr (${meta.ailmentChance}%)", color = Color(0xFFFFEBEE), textColor = Color(0xFFC62828))
                    }
                    if (meta.flinchChance > 0) {
                        MetaChip(text = "Retroceso: ${meta.flinchChance}%", color = Color(0xFFFFF3E0), textColor = Color(0xFFE65100))
                    }
                    if (meta.critRate > 0) {
                        MetaChip(text = "Crítico: +${meta.critRate}", color = Color(0xFFEDE7F6), textColor = Color(0xFF5E35B1))
                    }
                    if (meta.drain != 0) {
                        if (meta.drain > 0) {
                            MetaChip(text = "Drenado: ${meta.drain}%", color = Color(0xFFE0F7FA), textColor = Color(0xFF00838F))
                        } else {
                            MetaChip(text = "Recoil: ${meta.drain}%", color = Color(0xFFFBE9E7), textColor = Color(0xFFD84315))
                        }
                    }
                    if (meta.minHits != null && meta.maxHits != null) {
                        MetaChip(text = "Golpes: ${meta.minHits}-${meta.maxHits} veces", color = Color(0xFFF1F8E9), textColor = Color(0xFF558B2F))
                    }
                    if (meta.minTurns != null && meta.maxTurns != null) {
                        MetaChip(text = "Turnos: ${meta.minTurns}-${meta.maxTurns}", color = Color(0xFFECEFF1), textColor = Color(0xFF37474F))
                    }
                }

                if (movimiento.statChanges.isNotEmpty()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text("Estadísticas:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        movimiento.statChanges.forEach { changeSlot ->
                            val signo = if (changeSlot.change > 0) "+" else ""
                            Box(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(4.dp))
                                    .padding(horizontal = 5.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "${StatTranslations.translate(changeSlot.stat.name)} ($signo${changeSlot.change})",
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoTag(label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = "$label: ", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = value, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun MetaChip(text: String, color: Color, textColor: Color) {
    Box(
        modifier = Modifier
            .background(color, RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 3.dp)
    ) {
        Text(text = text, style = MaterialTheme.typography.labelSmall, color = textColor, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun FilaTablaEvolucion(nivel: Int, nombreEvolucion: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE8F5E9), RoundedCornerShape(4.dp))
            .border(1.dp, Color(0xFFC8E6C9), RoundedCornerShape(4.dp))
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$nivel",
            modifier = Modifier.weight(1.2f),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center,
            color = Color(0xFF1B5E20)
        )
        Row(
            modifier = Modifier.weight(6.5f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(Icons.Default.AccountTree, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(16.dp))
            Text("¡Evolución por Nivel! Cambia a:", style = MaterialTheme.typography.labelMedium, color = Color(0xFF1B5E20))
            Text(nombreEvolucion.uppercase(), style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20))
        }
    }
}