package com.unsa.pokeayuda.data.remote.model.evolution

import com.google.gson.annotations.SerializedName

data class EvolutionNodeResult(
    val name: String,
    @SerializedName("id_evolution") val idEvolution: String? = null,
    val evolution_details: List<EvolutionDetailResult> = emptyList(),
    val evolves_to: List<EvolutionNodeResult>
)