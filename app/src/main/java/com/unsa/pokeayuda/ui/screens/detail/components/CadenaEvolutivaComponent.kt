package com.unsa.pokeayuda.ui.screens.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.unsa.pokeayuda.data.remote.model.evolution.EvolutionNodeResult
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CadenaEvolutivaComponent(
    evolucionDetalle: EvolutionNodeResult,
    modifier: Modifier = Modifier
) {
    val etapasEvolutivas = remember(evolucionDetalle) {
        val lista = mutableListOf<EvolutionNodeResult>()
        var nodoActual: EvolutionNodeResult? = evolucionDetalle

        while (nodoActual != null) {
            lista.add(nodoActual)
            nodoActual = nodoActual.evolves_to.firstOrNull()
        }
        lista
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        etapasEvolutivas.forEachIndexed { index, nodo ->
            if (index > 0 && nodo.evolution_details.isNotEmpty()) {
                val detalle = nodo.evolution_details.first()

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDownward,
                        contentDescription = "Evoluciona a",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )

                    // Texto descriptivo del método de evolución
                    val textoRequisito = cuandoEvoluciona(
                        method = detalle.method,
                        level = detalle.level,
                        item = detalle.item
                    )

                    Text(
                        text = textoRequisito,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = nodo.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        nodo.idEvolution?.let { id ->
                            Text(
                                text = "N° Nacional: #$id",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun cuandoEvoluciona(method: String?, level: Int?, item: String?): String {
    return when (method) {
        "level-up" -> if (level != null) "Nivel $level" else "Subir de nivel"
        "use-item" -> if (item != null) "Usar ${item.replace("-", " ").uppercase()}" else "Usar objeto"
        "trade" -> "Por Intercambio"
        else -> method?.replace("-", " ")?.replaceFirstChar { it.uppercase() } ?: "Condición especial"
    }
}