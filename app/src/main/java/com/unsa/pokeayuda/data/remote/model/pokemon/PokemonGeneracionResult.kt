package com.unsa.pokeayuda.data.remote.model.pokemon

import com.unsa.pokeayuda.data.remote.model.move.MoveFinalDto

data class PokemonGeneracionResult(
    val id: Int,
    val name: String,
    val sprites: String?,
    val abilities: List<AbilitySlotDto>,
    val stats: List<StatSlotDto>,
    val types: List<TypeSlotDto>,
    val moves: Map<String, Map<String, List<MoveFinalDto>>>
)