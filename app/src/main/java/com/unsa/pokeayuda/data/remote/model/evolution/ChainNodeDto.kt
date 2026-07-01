package com.unsa.pokeayuda.data.remote.model.evolution

import com.google.gson.annotations.SerializedName
import com.unsa.pokeayuda.data.remote.model.shared.NamedApiResourceDto

data class ChainNodeDto(
    @SerializedName("species") val species: NamedApiResourceDto,
    @SerializedName("evolves_to") val evolvesTo: List<ChainNodeDto>,
    @SerializedName("evolution_details") val evolutionDetails: List<EvolutionDetailDto>
)