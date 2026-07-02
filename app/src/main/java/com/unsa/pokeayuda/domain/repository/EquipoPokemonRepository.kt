package com.unsa.pokeayuda.domain.repository

import com.unsa.pokeayuda.data.local.entity.EquipoPokemonEntity

interface EquipoPokemonRepository {
    suspend fun getAll(): List<EquipoPokemonEntity>
    suspend fun getByGeneracion(idGeneracion: Int): List<EquipoPokemonEntity>
    suspend fun getId(id: Int): EquipoPokemonEntity?
    suspend fun getId(idPokemon: Int, idGeneracion: Int): EquipoPokemonEntity?
    suspend fun insert(entity: EquipoPokemonEntity)
    suspend fun update(entity: EquipoPokemonEntity)
    suspend fun deleteId(id: Int)
    suspend fun deleteAll()
    suspend fun getSizeBytes(): Long
}