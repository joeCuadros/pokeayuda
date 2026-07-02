package com.unsa.pokeayuda.ui.screens.battle

import com.unsa.pokeayuda.data.remote.model.pokemon.PokemonGeneracionResult

data class BattleState(
    val equipoPokemon: List<PokemonGeneracionResult> = emptyList(),
    val rivalPokemon: PokemonGeneracionResult? = null,
    val nombreGeneracionActual: String = "",
    val idGeneracionActual: Int = 0,
    val busquedaRivalTexto: String = "",
    val nombresPokemonDisponibles: List<String> = emptyList(),
    val nombresPokemonFiltrados: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingRival: Boolean = false,
    val error: String? = null,
    val statOrdenadoPor: String? = null,
    val statOrdenAscendente: Boolean = false,
    val tablaStats: List<PokemonStatRow> = emptyList(),
)

data class PokemonStatRow(
    val id: Int,
    val nombre: String,
    val esRival: Boolean,
    val stats: Map<String, Int>
)

data class PokemonCompact(
    val nombre: String,
    val tipos: List<String>
)