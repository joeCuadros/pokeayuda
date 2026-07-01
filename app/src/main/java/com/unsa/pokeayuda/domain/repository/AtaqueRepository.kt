package com.unsa.pokeayuda.domain.repository

import com.unsa.pokeayuda.data.remote.model.move.MoveGeneracionResult

interface AtaqueRepository {
    suspend fun getAll(): List<MoveGeneracionResult>
    suspend fun getId(idPokemon: Int, idGeneracion: Int, nombreAtaque: String, nombreGeneracion: String): MoveGeneracionResult?
    suspend fun deleteId(idPokemon: Int, idGeneracion: Int)
    suspend fun deleteAll()
    suspend fun getSizeBytes(): Long
}