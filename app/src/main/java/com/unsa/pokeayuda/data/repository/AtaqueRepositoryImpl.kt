package com.unsa.pokeayuda.data.repository

import android.util.Log
import com.google.gson.Gson
import com.unsa.pokeayuda.data.local.dao.AtaqueDao
import com.unsa.pokeayuda.data.local.datastore.AppPreferences
import com.unsa.pokeayuda.data.local.entity.AtaqueEntity
import com.unsa.pokeayuda.data.remote.PokemonRemoteDataSource
import com.unsa.pokeayuda.data.remote.model.move.MoveGeneracionResult
import com.unsa.pokeayuda.domain.repository.AtaqueRepository
import kotlinx.coroutines.flow.firstOrNull
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AtaqueRepositoryImpl @Inject constructor(
    private val remoteDataSource: PokemonRemoteDataSource,
    private val ataqueDao: AtaqueDao,
    private val appPreferences: AppPreferences,
    private val gson: Gson
) : AtaqueRepository {

    override suspend fun getAll(): List<MoveGeneracionResult> {
        return try {
            ataqueDao.getAll().map { gson.fromJson(it.data, MoveGeneracionResult::class.java) }
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al obtener todos los ataques desde Room: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getId(idAtaque: Int, idGeneracion: Int, nombreAtaque: String, nombreGeneracion: String): MoveGeneracionResult? {
        return try {
            val local = ataqueDao.getId(idAtaque, idGeneracion)
            if (local == null) {
                fetchAndSave(idAtaque, idGeneracion, nombreAtaque, nombreGeneracion)
            } else {
                val diasConfigurados = appPreferences.syncDays.firstOrNull() ?: 7
                val currentTime = System.currentTimeMillis()
                if ((currentTime - local.fecha) > TimeUnit.DAYS.toMillis(diasConfigurados.toLong())) {
                    fetchAndSave(idAtaque, idGeneracion, nombreAtaque, nombreGeneracion)
                } else {
                    gson.fromJson(local.data, MoveGeneracionResult::class.java)
                }
            }
        } catch (e: Exception) {
            Log.d("DEBUG", "Error en sincronizacion local/remota de Ataque: ${e.message}")
            null
        }
    }

    private suspend fun fetchAndSave(idAtaque: Int, idGeneracion: Int, nombreAtaque: String, nombreGeneracion: String): MoveGeneracionResult? {
        val remoteData = remoteDataSource.obtenerMasInfoAtaque(nombreAtaque, nombreGeneracion)
        if (remoteData != null) {
            val jsonStr = gson.toJson(remoteData)
            val entity = AtaqueEntity(
                idAtaque = idAtaque,
                idGeneracion = idGeneracion,
                nombrePokemon = nombreAtaque,
                nombreGeneracion = nombreGeneracion,
                fecha = System.currentTimeMillis(),
                data = jsonStr
            )
            ataqueDao.insert(entity)
            return remoteData
        } else {
            Log.d("DEBUG", "Fallo al consumir remotamente Ataque: $nombreAtaque")
        }
        return null
    }

    override suspend fun deleteId(idAtaque: Int, idGeneracion: Int) {
        try {
            ataqueDao.deleteId(idAtaque, idGeneracion)
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al eliminar Ataque por ID: ${e.message}")
        }
    }

    override suspend fun deleteAll() {
        try {
            ataqueDao.deleteAll()
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al eliminar todos los ataques: ${e.message}")
        }
    }

    override suspend fun getSizeBytes(): Long {
        return try {
            ataqueDao.getSizeBytes()
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al obtener bytes de Ataque: ${e.message}")
            0L
        }
    }
}