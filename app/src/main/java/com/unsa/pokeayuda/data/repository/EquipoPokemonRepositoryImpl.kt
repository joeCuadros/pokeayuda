package com.unsa.pokeayuda.data.repository

import android.util.Log
import com.unsa.pokeayuda.data.local.dao.EquipoPokemonDao
import com.unsa.pokeayuda.data.local.entity.EquipoPokemonEntity
import com.unsa.pokeayuda.domain.repository.EquipoPokemonRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EquipoPokemonRepositoryImpl @Inject constructor(
    private val equipoPokemonDao: EquipoPokemonDao
) : EquipoPokemonRepository {

    override suspend fun getAll(): List<EquipoPokemonEntity> {
        return try {
            equipoPokemonDao.getAll()
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al obtener equipo desde Room: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getByGeneracion(idGeneracion: Int): List<EquipoPokemonEntity> {
        return try {
            equipoPokemonDao.getByGeneracion(idGeneracion)
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al obtener equipo por generación desde Room: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getId(id: Int): EquipoPokemonEntity? {
        return try {
            equipoPokemonDao.getId(id)
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al obtener miembro del equipo por ID: ${e.message}")
            null
        }
    }

    override suspend fun insert(entity: EquipoPokemonEntity) {
        try {
            equipoPokemonDao.insert(entity)
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al insertar en equipo_pokemon: ${e.message}")
        }
    }

    override suspend fun update(entity: EquipoPokemonEntity) {
        try {
            equipoPokemonDao.update(entity)
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al actualizar en equipo_pokemon: ${e.message}")
        }
    }

    override suspend fun deleteId(id: Int) {
        try {
            equipoPokemonDao.deleteId(id)
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al eliminar del equipo por ID: ${e.message}")
        }
    }

    override suspend fun deleteAll() {
        try {
            equipoPokemonDao.deleteAll()
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al vaciar tabla equipo_pokemon: ${e.message}")
        }
    }

    override suspend fun getSizeBytes(): Long {
        return try {
            equipoPokemonDao.getSizeBytes()
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al obtener bytes de equipo_pokemon: ${e.message}")
            0L
        }
    }
}