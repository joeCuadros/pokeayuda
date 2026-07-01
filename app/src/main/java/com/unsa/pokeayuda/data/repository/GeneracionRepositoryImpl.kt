package com.unsa.pokeayuda.data.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.unsa.pokeayuda.data.local.dao.GeneracionPokemonDao
import com.unsa.pokeayuda.data.local.datastore.AppPreferences
import com.unsa.pokeayuda.data.local.entity.GeneracionPokemonEntity
import com.unsa.pokeayuda.data.remote.PokemonRemoteDataSource
import com.unsa.pokeayuda.domain.repository.GeneracionRepository
import kotlinx.coroutines.flow.firstOrNull
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeneracionRepositoryImpl @Inject constructor(
    private val remoteDataSource: PokemonRemoteDataSource,
    private val generacionPokemonDao: GeneracionPokemonDao,
    private val appPreferences: AppPreferences,
    private val gson: Gson
) : GeneracionRepository {

    private val listType = object : TypeToken<List<String>>() {}.type

    override suspend fun getAll(): List<String> {
        return try {
            generacionPokemonDao.getAll().flatMap { gson.fromJson<List<String>>(it.data, listType) }.distinct()
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al obtener todas las generaciones de Room: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getId(id: Int, nombreGeneracion: String): List<String> {
        return try {
            val local = generacionPokemonDao.getId(id)
            if (local == null) {
                fetchAndSave(id, nombreGeneracion)
            } else {
                val diasConfigurados = appPreferences.syncDays.firstOrNull() ?: 7
                val currentTime = System.currentTimeMillis()
                if ((currentTime - local.fecha) > TimeUnit.DAYS.toMillis(diasConfigurados.toLong())) {
                    fetchAndSave(id, nombreGeneracion)
                } else {
                    gson.fromJson(local.data, listType)
                }
            }
        } catch (e: Exception) {
            Log.d("DEBUG", "Error en sincronizacion local/remota de Generacion: ${e.message}")
            emptyList()
        }
    }

    private suspend fun fetchAndSave(id: Int, nombreGeneracion: String): List<String> {
        val remoteData = remoteDataSource.getPokemonNamesByGeneration(nombreGeneracion)
        if (remoteData.isNotEmpty()) {
            val jsonStr = gson.toJson(remoteData)
            val entity = GeneracionPokemonEntity(
                id = id,
                nombre = nombreGeneracion,
                fecha = System.currentTimeMillis(),
                data = jsonStr
            )
            generacionPokemonDao.insert(entity)
        } else {
            Log.d("DEBUG", "Fallo al consumir remotamente o lista vacia para Generacion: $nombreGeneracion")
        }
        return remoteData
    }

    override suspend fun deleteId(id: Int) {
        try {
            generacionPokemonDao.deleteId(id)
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al eliminar Generacion por ID: ${e.message}")
        }
    }

    override suspend fun deleteAll() {
        try {
            generacionPokemonDao.deleteAll()
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al eliminar todas las generaciones: ${e.message}")
        }
    }

    override suspend fun getSizeBytes(): Long {
        return try {
            generacionPokemonDao.getSizeBytes()
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al obtener bytes de Generacion: ${e.message}")
            0L
        }
    }
}