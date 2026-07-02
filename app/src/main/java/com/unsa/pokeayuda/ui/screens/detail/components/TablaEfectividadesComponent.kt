package com.unsa.pokeayuda.ui.screens.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.unsa.pokeayuda.ui.components.PokemonTypeChip

@Composable
fun TablaEfectividadesComponent(
    efectividades: Map<String, Float>,
    modifier: Modifier = Modifier
) {
    // Filtramos la matriz completa en los multiplicadores que me pediste
    val grupoX4 = efectividades.filter { it.value == 4.0f }.keys
    val grupoX2 = efectividades.filter { it.value == 2.0f }.keys
    val grupoX1 = efectividades.filter { it.value == 1.0f }.keys
    val grupoX05 = efectividades.filter { it.value == 0.5f }.keys
    val grupoX025 = efectividades.filter { it.value == 0.25f }.keys
    val grupoX0 = efectividades.filter { it.value == 0.0f }.keys

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.15f))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        SeccionMultiplicador(label = "Daño x4", tipos = grupoX4, colorBadge = Color(0xFFD32F2F))
        SeccionMultiplicador(label = "Daño x2", tipos = grupoX2, colorBadge = Color(0xFFE65100))
        SeccionMultiplicador(label = "Daño x1", tipos = grupoX1, colorBadge = Color(0xFF757575))
        SeccionMultiplicador(label = "Daño x0.5", tipos = grupoX05, colorBadge = Color(0xFF388E3C))
        SeccionMultiplicador(label = "Daño x0.25", tipos = grupoX025, colorBadge = Color(0xFF1B5E20))
        SeccionMultiplicador(label = "Daño x0", tipos = grupoX0, colorBadge = Color(0xFF455A64))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SeccionMultiplicador(
    label: String,
    tipos: Set<String>,
    colorBadge: Color
) {
    if (tipos.isEmpty()) return

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(colorBadge.copy(alpha = 0.15f))
                .padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Black,
                color = colorBadge
            )
        }

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxWidth().padding(start = 4.dp)
        ) {
            tipos.forEach { tipoNombre ->
                PokemonTypeChip(type = tipoNombre)
            }
        }
    }
}