package com.unsa.pokeayuda.domain.repository

import com.unsa.pokeayuda.data.remote.model.ability.AbilityGeneracionResult

interface HabilidadRepository {
    suspend fun getAll(): List<AbilityGeneracionResult>
    suspend fun getId(idPokemon: Int, idGeneracion: Int, nombreHabilidad: String, nombreGeneracion: String): AbilityGeneracionResult?
    suspend fun deleteId(idPokemon: Int, idGeneracion: Int)
    suspend fun deleteAll()
    suspend fun getSizeBytes(): Long
}