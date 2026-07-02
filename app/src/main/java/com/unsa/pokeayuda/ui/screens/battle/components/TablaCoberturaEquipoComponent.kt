package com.unsa.pokeayuda.ui.screens.battle.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unsa.pokeayuda.ui.components.PokemonTypeChip
import com.unsa.pokeayuda.ui.screens.battle.PokemonCompact
import com.unsa.pokeayuda.utils.PokemonTypeVS
import com.unsa.pokeayuda.utils.translations.TypeTranslations

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TablaCoberturaEquipoComponent(
    genKey: String,
    tiposRival: List<String>,
    miEquipo: List<PokemonCompact>,
    modifier: Modifier = Modifier
) {
    if (tiposRival.isEmpty() || miEquipo.isEmpty()) {
        Text(
            text = "Selecciona un rival y configura tu equipo para calcular la cobertura.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(16.dp)
        )
        return
    }

    val matrizRival = PokemonTypeVS.calcularDebilidades(genKey, tiposRival)

    val miEquipoOrdenado = miEquipo.sortedWith { p1, p2 ->
        val m1 = p1.tipos.map { matrizRival[it] ?: 1.0f }.sortedByDescending { it }
        val m2 = p2.tipos.map { matrizRival[it] ?: 1.0f }.sortedByDescending { it }

        val p1Max = m1.getOrNull(0) ?: 1.0f
        val p1Sec = m1.getOrNull(1) ?: 1.0f // Si no tiene 2do tipo, cuenta como neutro (x1) para el desempate

        val p2Max = m2.getOrNull(0) ?: 1.0f
        val p2Sec = m2.getOrNull(1) ?: 1.0f

        if (p1Max != p2Max) {
            p2Max.compareTo(p1Max)
        } else {
            p2Sec.compareTo(p1Sec)
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .padding(vertical = 10.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Mi Equipo",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(0.45f)
            )

            Row(
                modifier = Modifier.weight(0.55f),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Rival: ",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
                tiposRival.forEach { tipo ->
                    PokemonTypeChip(type = tipo)
                }
            }
        }

        miEquipoOrdenado.forEach { pokemon ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(vertical = 12.dp, horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(0.45f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = pokemon.nombre,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        pokemon.tipos.forEach { tipo ->
                            PokemonTypeChip(type = tipo)
                        }
                    }
                }

                FlowRow(
                    modifier = Modifier.weight(0.55f),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val efectividadesOfensivas = pokemon.tipos
                        .map { miTipo -> miTipo to (matrizRival[miTipo] ?: 1.0f) }
                        .sortedByDescending { it.second }

                    efectividadesOfensivas.forEach { (tipoAtacante, multiplicador) ->
                        BadgeMultiplicadorOfensivo(tipo = tipoAtacante, multiplicador = multiplicador)
                    }
                }
            }
        }
    }
}

@Composable
private fun BadgeMultiplicadorOfensivo(tipo: String, multiplicador: Float) {
    val (colorTexto, colorFondo, textoMultiplicador) = when (multiplicador) {
        4.0f -> Triple(Color(0xFFD32F2F), Color(0xFFFFEBEE), "x4")
        2.0f -> Triple(Color(0xFFE65100), Color(0xFFFFE0B2), "x2")
        1.0f -> Triple(Color(0xFF424242), Color(0xFFF5F5F5), "x1")
        0.5f -> Triple(Color(0xFF388E3C), Color(0xFFE8F5E9), "x0.5")
        0.25f -> Triple(Color(0xFF1B5E20), Color(0xFFC8E6C9), "x0.25")
        else -> Triple(Color(0xFFFFFFFF), Color(0xFF37474F), "x0")
    }

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(colorFondo)
            .padding(horizontal = 6.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = TypeTranslations.translate(tipo),
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
            fontWeight = FontWeight.Bold,
            color = colorTexto.copy(alpha = 0.8f)
        )
        Text(
            text = textoMultiplicador,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
            fontWeight = FontWeight.Black,
            color = colorTexto
        )
    }
}