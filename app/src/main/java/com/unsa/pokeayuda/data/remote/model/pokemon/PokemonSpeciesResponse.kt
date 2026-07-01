package com.unsa.pokeayuda.data.remote.model.pokemon

import com.google.gson.annotations.SerializedName
import com.unsa.pokeayuda.data.remote.model.evolution.EvolutionChainUrlDto

data class PokemonSpeciesResponse(
    @SerializedName("evolution_chain") val evolutionChain: EvolutionChainUrlDto
)