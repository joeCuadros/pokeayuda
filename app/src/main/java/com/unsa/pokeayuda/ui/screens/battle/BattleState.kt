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
    val matrizEfectividadEquipo: List<PokemonEfectividadMatchup> = emptyList()
)

data class PokemonStatRow(
    val id: Int,
    val nombre: String,
    val esRival: Boolean,
    val stats: Map<String, Int>
)

data class PokemonEfectividadMatchup(
    val idPokemon: Int,
    val nombrePokemon: String,
    val tablaOfensiva: List<EfectividadRow>,
    val tablaDefensiva: List<EfectividadRow>
)

data class EfectividadRow(
    val tipoAtacante: String,
    val tipoDefensorCombinado: String,
    val multiplicador: Float
)