package com.unsa.pokeayuda.ui.screens.detail

import com.unsa.pokeayuda.data.remote.model.ability.AbilityGeneracionResult
import com.unsa.pokeayuda.data.remote.model.evolution.EvolutionNodeResult
import com.unsa.pokeayuda.data.remote.model.move.MoveGeneracionResult
import com.unsa.pokeayuda.data.remote.model.pokemon.PokemonGeneracionResult
import com.unsa.pokeayuda.data.remote.model.type.TypeGeneracionResult

data class DetailState(
    val pokemonId: Int = 0,
    val nombreGeneracionActual: String = "",
    val isLoadingPokemon: Boolean = false,
    val pokemonDetalle: PokemonGeneracionResult? = null,
    val errorPokemon: String? = null,

    val isLoadingEvoluciones: Boolean = false,
    val evolucionDetalle: EvolutionNodeResult? = null,
    val errorEvoluciones: String? = null,

    val isLoadingAtaques: Boolean = false,
    val ataquesVisibles: Map<String, Map<Int, List<MoveGeneracionResult>>> = emptyMap(),
    val juegoSeleccionado: String = "",
    val ataqueSeleccionadoDetalle: MoveGeneracionResult? = null,
    val errorAtaques: String? = null,

    val isLoadingHabilidades: Boolean = false,
    val habilidadesVisibles: List<AbilityGeneracionResult> = emptyList(),
    val habilidadSeleccionadaDetalle: AbilityGeneracionResult? = null,
    val errorHabilidades: String? = null,

    val isLoadingTipos: Boolean = false,
    val tiposVisibles: List<TypeGeneracionResult> = emptyList(),
    val errorTipos: String? = null
)