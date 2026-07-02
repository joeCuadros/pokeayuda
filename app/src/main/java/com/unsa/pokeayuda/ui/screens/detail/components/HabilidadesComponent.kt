package com.unsa.pokeayuda.ui.screens.detail.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.unsa.pokeayuda.data.remote.model.ability.AbilityGeneracionResult // Asegúrate de que apunte a tu modelo real
import java.util.Locale

@Composable
fun HabilidadesComponent(
    habilidadesVisibles: List<AbilityGeneracionResult>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        habilidadesVisibles.forEach { habilidad ->
            TarjetaHabilidadInteractiva(habilidad = habilidad)
        }
    }
}

@Composable
private fun TarjetaHabilidadInteractiva(
    habilidad: AbilityGeneracionResult
) {
    var expandido by remember { mutableStateOf(false) }

    // Elegimos un texto de ambientación (flavor_text) aleatorio si existe, o un texto por defecto
    val textoAmbientacion = remember(habilidad.flavorText) {
        if (habilidad.flavorText.isNotEmpty()) {
            habilidad.flavorText.random()
                .replace("\n", " ") // Limpia saltos de línea feos del JSON de la API
                .replace("\u000c", " ")
        } else {
            "Sin descripción disponible para esta generación."
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            .clickable { expandido = !expandido }
            .animateContentSize(animationSpec = tween(durationMillis = 300)) // Animación suave al expandir
            .padding(16.dp)
    ) {
        // Cabecera: Nombre en Español e Ícono de Expansión
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = habilidad.nameEs.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Icon(
                imageVector = if (expandido) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = if (expandido) "Ver menos" else "Ver más",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Flavor Text Aleatorio por Defecto
        Text(
            text = "\"$textoAmbientacion\"",
            style = MaterialTheme.typography.bodyMedium,
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Contenido Expandible: Efecto Completo y Corto
        if (expandido) {
            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(12.dp))

            // Short Effect (Efecto Corto)
            habilidad.shortEffect?.let { efectoCorto ->
                Text(
                    text = "Resumen del efecto:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = efectoCorto,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // Effect (Efecto Completo)
            habilidad.effect?.let { efectoCompleto ->
                Text(
                    text = "Efecto detallado:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = efectoCompleto,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            if (habilidad.shortEffect == null && habilidad.effect == null) {
                Text(
                    text = "No hay datos de efectos técnicos adicionales.",
                    style = MaterialTheme.typography.bodySmall,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}