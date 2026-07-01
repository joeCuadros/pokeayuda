package com.unsa.pokeayuda.data.remote.model.generation

import com.google.gson.annotations.SerializedName
import com.unsa.pokeayuda.data.remote.model.pokemon.PokemonSpeciesDto

data class GenerationResponse(
    @SerializedName("pokemon_species")
    val pokemonSpecies: List<PokemonSpeciesDto>
)