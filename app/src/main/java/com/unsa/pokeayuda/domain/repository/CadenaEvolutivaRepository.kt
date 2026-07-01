package com.unsa.pokeayuda.domain.repository

import com.unsa.pokeayuda.data.remote.model.evolution.EvolutionNodeResult

interface CadenaEvolutivaRepository {
    suspend fun getAll(): List<EvolutionNodeResult>
    suspend fun getId(id: Int, nombrePokemon: String): EvolutionNodeResult?
    suspend fun deleteId(id: Int)
    suspend fun deleteAll()
    suspend fun getSizeBytes(): Long
}