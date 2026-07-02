package com.unsa.pokeayuda.data.repository

import android.util.Log
import com.google.gson.Gson
import com.unsa.pokeayuda.data.local.dao.TipoDao
import com.unsa.pokeayuda.data.local.datastore.AppPreferences
import com.unsa.pokeayuda.data.local.entity.TipoEntity
import com.unsa.pokeayuda.data.remote.PokemonRemoteDataSource
import com.unsa.pokeayuda.data.remote.model.type.TypeGeneracionResult
import com.unsa.pokeayuda.domain.repository.TipoRepository
import com.unsa.pokeayuda.utils.constants.GenerationConstants
import kotlinx.coroutines.flow.firstOrNull
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TipoRepositoryImpl @Inject constructor(
    private val remoteDataSource: PokemonRemoteDataSource,
    private val tipoDao: TipoDao,
    private val appPreferences: AppPreferences,
    private val gson: Gson
) : TipoRepository {

    override suspend fun getAll(): List<TypeGeneracionResult> {
        return try {
            tipoDao.getAll().map { gson.fromJson(it.data, TypeGeneracionResult::class.java) }
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al obtener todos los tipos desde Room: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getId(idTipo: Int, idGeneracion: Int, nombreTipo: String, nombreGeneracion: String): TypeGeneracionResult? {
        return try {
            if (idTipo <= 0) {
                return fetchAndSave(nombreTipo, nombreGeneracion)
            }
            val local = tipoDao.getId(idTipo, idGeneracion)
            if (local == null) {
                fetchAndSave(idTipo, idGeneracion, nombreTipo, nombreGeneracion)
            } else {
                val diasConfigurados = appPreferences.syncDays.firstOrNull() ?: 7
                val currentTime = System.currentTimeMillis()
                if ((currentTime - local.fecha) > TimeUnit.DAYS.toMillis(diasConfigurados.toLong())) {
                    fetchAndSave(idTipo, idGeneracion, nombreTipo, nombreGeneracion)
                } else {
                    gson.fromJson(local.data, TypeGeneracionResult::class.java)
                }
            }
        } catch (e: Exception) {
            Log.d("DEBUG", "Error en sincronizacion local/remota de Tipo: ${e.message}")
            null
        }
    }

    private suspend fun fetchAndSave(idTipo: Int, idGeneracion: Int, nombreTipo: String, nombreGeneracion: String): TypeGeneracionResult? {
        val remoteData = remoteDataSource.obtenerInfoTipo(nombreTipo, nombreGeneracion)
        if (remoteData != null) {
            val jsonStr = gson.toJson(remoteData)
            val entity = TipoEntity(
                idTipo = idTipo,
                idGeneracion = idGeneracion,
                nombrePokemon = nombreTipo,
                nombreGeneracion = nombreGeneracion,
                fecha = System.currentTimeMillis(),
                data = jsonStr
            )
            tipoDao.insert(entity)
            return remoteData
        } else {
            Log.d("DEBUG", "Fallo al consumir remotamente Tipo: $nombreTipo")
        }
        return null
    }

    private suspend fun fetchAndSave(nombreTipo: String, nombreGeneracion: String): TypeGeneracionResult? {
        val remoteData = remoteDataSource.obtenerInfoTipo(nombreTipo, nombreGeneracion)
        if (remoteData != null) {
            val jsonStr = gson.toJson(remoteData)
            val idGenReal = GenerationConstants.getId(nombreGeneracion) ?: 1
            val entity = TipoEntity(
                idTipo = remoteData.id,
                idGeneracion = idGenReal,
                nombrePokemon = nombreTipo,
                nombreGeneracion = nombreGeneracion,
                fecha = System.currentTimeMillis(),
                data = jsonStr
            )
            tipoDao.insert(entity)
            return remoteData
        } else {
            Log.d("DEBUG", "Fallo al consumir remotamente Tipo: $nombreTipo")
        }
        return null
    }

    override suspend fun deleteId(idTipo: Int, idGeneracion: Int) {
        try {
            tipoDao.deleteId(idTipo, idGeneracion)
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al eliminar Tipo por ID: ${e.message}")
        }
    }

    override suspend fun deleteAll() {
        try {
            tipoDao.deleteAll()
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al eliminar todos los tipos: ${e.message}")
        }
    }

    override suspend fun getSizeBytes(): Long {
        return try {
            tipoDao.getSizeBytes()
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al obtener bytes de Tipo: ${e.message}")
            0L
        }
    }
}