package com.unsa.pokeayuda.data.repository

import android.util.Log
import com.google.gson.Gson
import com.unsa.pokeayuda.data.local.dao.CadenaEvolutivaDao
import com.unsa.pokeayuda.data.local.datastore.AppPreferences
import com.unsa.pokeayuda.data.local.entity.CadenaEvolutivaEntity
import com.unsa.pokeayuda.data.remote.PokemonRemoteDataSource
import com.unsa.pokeayuda.data.remote.model.evolution.EvolutionNodeResult
import com.unsa.pokeayuda.domain.repository.CadenaEvolutivaRepository
import kotlinx.coroutines.flow.firstOrNull
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CadenaEvolutivaRepositoryImpl @Inject constructor(
    private val remoteDataSource: PokemonRemoteDataSource,
    private val cadenaEvolutivaDao: CadenaEvolutivaDao,
    private val appPreferences: AppPreferences,
    private val gson: Gson
) : CadenaEvolutivaRepository {

    override suspend fun getAll(): List<EvolutionNodeResult> {
        return try {
            cadenaEvolutivaDao.getAll().map { gson.fromJson(it.data, EvolutionNodeResult::class.java) }
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al obtener todas las cadenas evolutivas de Room: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getId(id: Int, nombrePokemon: String): EvolutionNodeResult? {
        return try {
            val local = cadenaEvolutivaDao.getId(id)
            if (local == null) {
                fetchAndSave(id, nombrePokemon)
            } else {
                val diasConfigurados = appPreferences.syncDays.firstOrNull() ?: 7
                val currentTime = System.currentTimeMillis()
                if ((currentTime - local.fecha) > TimeUnit.DAYS.toMillis(diasConfigurados.toLong())) {
                    fetchAndSave(id, nombrePokemon)
                } else {
                    gson.fromJson(local.data, EvolutionNodeResult::class.java)
                }
            }
        } catch (e: Exception) {
            Log.d("DEBUG", "Error en sincronizacion local/remota de Cadena Evolutiva: ${e.message}")
            null
        }
    }

    private suspend fun fetchAndSave(id: Int, nombrePokemon: String): EvolutionNodeResult? {
        val remoteData = remoteDataSource.obtenerCadenaEvolutiva(nombrePokemon)
        if (remoteData != null) {
            val jsonStr = gson.toJson(remoteData)
            val entity = CadenaEvolutivaEntity(
                id = id,
                fecha = System.currentTimeMillis(),
                data = jsonStr
            )
            cadenaEvolutivaDao.insert(entity)
            return remoteData
        } else {
            Log.d("DEBUG", "Fallo al consumir remotamente Cadena Evolutiva para: $nombrePokemon")
        }
        return null
    }

    override suspend fun deleteId(id: Int) {
        try {
            cadenaEvolutivaDao.deleteId(id)
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al eliminar Cadena Evolutiva por ID: ${e.message}")
        }
    }

    override suspend fun deleteAll() {
        try {
            cadenaEvolutivaDao.deleteAll()
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al eliminar todas las cadenas evolutivas: ${e.message}")
        }
    }

    override suspend fun getSizeBytes(): Long {
        return try {
            cadenaEvolutivaDao.getSizeBytes()
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al obtener bytes de Cadena Evolutiva: ${e.message}")
            0L
        }
    }
}