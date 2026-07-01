package com.unsa.pokeayuda.domain.model

data class CacheSize(
    val pokemon: Long,
    val generation: Long,
    val evolutionChain: Long,
    val move: Long,
    val ability: Long,
    val type: Long
) {
    val total: Long
        get() = pokemon +
                generation +
                evolutionChain +
                move +
                ability +
                type
}