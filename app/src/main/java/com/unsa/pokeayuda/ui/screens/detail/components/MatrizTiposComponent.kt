package com.unsa.pokeayuda.ui.screens.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.unsa.pokeayuda.data.remote.model.type.TypeGeneracionResult
import com.unsa.pokeayuda.ui.components.PokemonTypeChip

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MatrizTiposComponent(
    tiposVisibles: List<TypeGeneracionResult>,
    modifier: Modifier = Modifier
) {
    // 1. Calcular la efectividad cruzada de los tipos del Pokémon
    val resultadosMatriz = remember(tiposVisibles) {
        val mapaEfectividades = mutableMapOf<String, Float>()
        val todosLosTiposExistentes = mutableSetOf<String>()
        tiposVisibles.forEach { tipo ->
            val relations = tipo.damageRelations

            // Recopilamos todos los nombres de tipos que interactúan para conocer el universo de tipos actual
            relations.doubleDamageFrom.forEach { todosLosTiposExistentes.add(it.name) }
            relations.halfDamageFrom.forEach { todosLosTiposExistentes.add(it.name) }
            relations.noDamageFrom.forEach { todosLosTiposExistentes.add(it.name) }
            relations.doubleDamageTo.forEach { todosLosTiposExistentes.add(it.name) }
            relations.halfDamageTo.forEach { todosLosTiposExistentes.add(it.name) }
            relations.noDamageTo.forEach { todosLosTiposExistentes.add(it.name) }

            // Aplicamos los multiplicadores de daño recibido (From)
            relations.doubleDamageFrom.forEach {
                mapaEfectividades[it.name] = (mapaEfectividades[it.name] ?: 1f) * 2f
            }
            relations.halfDamageFrom.forEach {
                mapaEfectividades[it.name] = (mapaEfectividades[it.name] ?: 1f) * 0.5f
            }
            relations.noDamageFrom.forEach {
                mapaEfectividades[it.name] = 0f
            }
        }
        // Rellenamos explícitamente con 1f los tipos que no sufrieron modificaciones
        todosLosTiposExistentes.forEach { nombreTipo ->
            if (!mapaEfectividades.containsKey(nombreTipo)) {
                mapaEfectividades[nombreTipo] = 1f
            }
        }

        mapaEfectividades
    }

    // Clasificar los resultados en sus respectivos grupos de daño
    val debilidadesX4 = resultadosMatriz.filter { it.value == 4f }.keys
    val debilidadesX2 = resultadosMatriz.filter { it.value == 2f }.keys
    val neutrosX1 = resultadosMatriz.filter { it.value == 1f }.keys
    val resistenciasX05 = resultadosMatriz.filter { it.value == 0.5f }.keys
    val resistenciasX025 = resultadosMatriz.filter { it.value == 0.24f || it.value == 0.25f }.keys // Tolerancia por flotantes
    val inmunidades = resultadosMatriz.filter { it.value == 0f }.keys

    // Renderizar la interfaz
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (debilidadesX4.isNotEmpty()) {
            FilaMatrizTipo(titulo = "Debilidades Súper Efectivas (x4)", tipos = debilidadesX4)
        }
        if (debilidadesX2.isNotEmpty()) {
            FilaMatrizTipo(titulo = "Debilidades (x2)", tipos = debilidadesX2)
        }
        if (neutrosX1.isNotEmpty()) {
            FilaMatrizTipo(titulo = "Daño Normal (x1)", tipos = neutrosX1)
        }
        if (resistenciasX05.isNotEmpty()) {
            FilaMatrizTipo(titulo = "Resistencias (x0.5)", tipos = resistenciasX05)
        }
        if (resistenciasX025.isNotEmpty()) {
            FilaMatrizTipo(titulo = "Súper Resistencias (x0.25)", tipos = resistenciasX025)
        }
        if (inmunidades.isNotEmpty()) {
            FilaMatrizTipo(titulo = "Inmunidades (x0)", tipos = inmunidades)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FilaMatrizTipo(
    titulo: String,
    tipos: Set<String>
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = titulo,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            tipos.sorted().forEach { nombreTipo ->
                PokemonTypeChip(type = nombreTipo)
            }
        }
    }
}