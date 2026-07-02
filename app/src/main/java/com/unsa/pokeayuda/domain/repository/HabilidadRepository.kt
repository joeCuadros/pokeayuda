package com.unsa.pokeayuda.domain.repository

import com.unsa.pokeayuda.data.remote.model.ability.AbilityGeneracionResult

interface HabilidadRepository {
    suspend fun getAll(): List<AbilityGeneracionResult>
    suspend fun getId(idHabilidad: Int, idGeneracion: Int, nombreHabilidad: String, nombreGeneracion: String): AbilityGeneracionResult?
    suspend fun deleteId(idHabilidad: Int, idGeneracion: Int)
    suspend fun deleteAll()
    suspend fun getSizeBytes(): Long
}