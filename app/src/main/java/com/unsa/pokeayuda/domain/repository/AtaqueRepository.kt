package com.unsa.pokeayuda.domain.repository

import com.unsa.pokeayuda.data.remote.model.move.MoveGeneracionResult

interface AtaqueRepository {
    suspend fun getAll(): List<MoveGeneracionResult>
    suspend fun getId(idAtaque: Int, idGeneracion: Int, nombreAtaque: String, nombreGeneracion: String): MoveGeneracionResult?
    suspend fun deleteId(idAtaque: Int, idGeneracion: Int)
    suspend fun deleteAll()
    suspend fun getSizeBytes(): Long
}