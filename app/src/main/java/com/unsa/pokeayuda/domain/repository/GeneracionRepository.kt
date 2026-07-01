package com.unsa.pokeayuda.domain.repository

interface GeneracionRepository {
    suspend fun getAll(): List<String>
    suspend fun getId(id: Int, nombreGeneracion: String): List<String>
    suspend fun deleteId(id: Int)
    suspend fun deleteAll()
    suspend fun getSizeBytes(): Long
}