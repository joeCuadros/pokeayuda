package com.unsa.pokeayuda.ui.screens.pokemon

import com.unsa.pokeayuda.data.local.entity.EquipoPokemonEntity
import com.unsa.pokeayuda.data.remote.model.pokemon.PokemonGeneracionResult

data class PokemonState(
    val nombreGeneracionActual: String = "",
    val idGeneracionActual: Int = 0,
    val nombresPokemonDisponibles: List<String> = emptyList(),
    val nombresPokemonFiltrados: List<String> = emptyList(),
    val busquedaTexto: String = "",
    val equipoActual: List<EquipoPokemonEntity> = emptyList(),
    val detallesPokemonCargados: Map<Int, PokemonGeneracionResult> = emptyMap(),
    val isLoading: Boolean = false,
    val error: String? = null
)