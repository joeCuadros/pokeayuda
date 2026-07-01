package com.unsa.pokeayuda.domain.repository

import com.unsa.pokeayuda.data.remote.model.type.TypeGeneracionResult

interface TipoRepository {
    suspend fun getAll(): List<TypeGeneracionResult>
    suspend fun getId(idPokemon: Int, idGeneracion: Int, nombreTipo: String, nombreGeneracion: String): TypeGeneracionResult?
    suspend fun deleteId(idPokemon: Int, idGeneracion: Int)
    suspend fun deleteAll()
    suspend fun getSizeBytes(): Long
}