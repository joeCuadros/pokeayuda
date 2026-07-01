package com.unsa.pokeayuda.data.remote.model.evolution

import com.google.gson.annotations.SerializedName

data class EvolutionChainResponse(
    @SerializedName("chain") val chain: ChainNodeDto
)