package com.unsa.pokeayuda.data.repository

import android.util.Log
import com.google.gson.Gson
import com.unsa.pokeayuda.data.local.dao.HabilidadDao
import com.unsa.pokeayuda.data.local.datastore.AppPreferences
import com.unsa.pokeayuda.data.local.entity.HabilidadEntity
import com.unsa.pokeayuda.data.remote.PokemonRemoteDataSource
import com.unsa.pokeayuda.data.remote.model.ability.AbilityGeneracionResult
import com.unsa.pokeayuda.domain.repository.HabilidadRepository
import kotlinx.coroutines.flow.firstOrNull
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HabilidadRepositoryImpl @Inject constructor(
    private val remoteDataSource: PokemonRemoteDataSource,
    private val habilidadDao: HabilidadDao,
    private val appPreferences: AppPreferences,
    private val gson: Gson
) : HabilidadRepository {

    override suspend fun getAll(): List<AbilityGeneracionResult> {
        return try {
            habilidadDao.getAll().map { gson.fromJson(it.data, AbilityGeneracionResult::class.java) }
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al obtener todas las habilidades desde Room: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getId(idPokemon: Int, idGeneracion: Int, nombreHabilidad: String, nombreGeneracion: String): AbilityGeneracionResult? {
        return try {
            val local = habilidadDao.getId(idPokemon, idGeneracion)
            if (local == null) {
                fetchAndSave(idPokemon, idGeneracion, nombreHabilidad, nombreGeneracion)
            } else {
                val diasConfigurados = appPreferences.syncDays.firstOrNull() ?: 7
                val currentTime = System.currentTimeMillis()
                if ((currentTime - local.fecha) > TimeUnit.DAYS.toMillis(diasConfigurados.toLong())) {
                    fetchAndSave(idPokemon, idGeneracion, nombreHabilidad, nombreGeneracion)
                } else {
                    gson.fromJson(local.data, AbilityGeneracionResult::class.java)
                }
            }
        } catch (e: Exception) {
            Log.d("DEBUG", "Error en sincronizacion local/remota de Habilidad: ${e.message}")
            null
        }
    }

    private suspend fun fetchAndSave(idPokemon: Int, idGeneracion: Int, nombreHabilidad: String, nombreGeneracion: String): AbilityGeneracionResult? {
        val remoteData = remoteDataSource.obtenerInfoHabilidad(nombreHabilidad, nombreGeneracion)
        if (remoteData != null) {
            val jsonStr = gson.toJson(remoteData)
            val entity = HabilidadEntity(
                idPokemon = idPokemon,
                idGeneracion = idGeneracion,
                nombrePokemon = nombreHabilidad,
                nombreGeneracion = nombreGeneracion,
                fecha = System.currentTimeMillis(),
                data = jsonStr
            )
            habilidadDao.insert(entity)
            return remoteData
        } else {
            Log.d("DEBUG", "Fallo al consumir remotamente Habilidad: $nombreHabilidad")
        }
        return null
    }

    override suspend fun deleteId(idPokemon: Int, idGeneracion: Int) {
        try {
            habilidadDao.deleteId(idPokemon, idGeneracion)
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al eliminar Habilidad por ID: ${e.message}")
        }
    }

    override suspend fun deleteAll() {
        try {
            habilidadDao.deleteAll()
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al eliminar todas las habilidades: ${e.message}")
        }
    }

    override suspend fun getSizeBytes(): Long {
        return try {
            habilidadDao.getSizeBytes()
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al obtener bytes de Habilidad: ${e.message}")
            0L
        }
    }
}