package com.unsa.pokeayuda.data.remote.model.pokemon

import com.google.gson.annotations.SerializedName

data class PokemonSpeciesDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)