package com.unsa.pokeayuda.data.repository

import android.util.Log
import com.google.gson.Gson
import com.unsa.pokeayuda.data.local.dao.PokemonDao
import com.unsa.pokeayuda.data.local.datastore.AppPreferences
import com.unsa.pokeayuda.data.local.entity.PokemonEntity
import com.unsa.pokeayuda.data.remote.PokemonRemoteDataSource
import com.unsa.pokeayuda.data.remote.model.pokemon.PokemonGeneracionResult
import com.unsa.pokeayuda.domain.repository.PokemonRepository
import com.unsa.pokeayuda.utils.constants.GenerationConstants
import kotlinx.coroutines.flow.firstOrNull
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepositoryImpl @Inject constructor(
    private val remoteDataSource: PokemonRemoteDataSource,
    private val pokemonDao: PokemonDao,
    private val appPreferences: AppPreferences,
    private val gson: Gson
) : PokemonRepository {

    override suspend fun getAll(): List<PokemonGeneracionResult> {
        return try {
            pokemonDao.getAll().map { gson.fromJson(it.data, PokemonGeneracionResult::class.java) }
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al obtener todos los pokemon desde Room: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getId(idPokemon: Int, idGeneracion: Int, nombrePokemon: String, nombreGeneracion: String): PokemonGeneracionResult? {
        return try {
            if (idPokemon <= 0) {
                return fetchAndSave(nombrePokemon, nombreGeneracion)
            }
            val local = pokemonDao.getId(idPokemon, idGeneracion)
            if (local == null) {
                fetchAndSave(idPokemon, idGeneracion, nombrePokemon, nombreGeneracion)
            } else {
                val diasConfigurados = appPreferences.syncDays.firstOrNull() ?: 7
                val currentTime = System.currentTimeMillis()
                if ((currentTime - local.fecha) > TimeUnit.DAYS.toMillis(diasConfigurados.toLong())) {
                    fetchAndSave(idPokemon, idGeneracion, nombrePokemon, nombreGeneracion)
                } else {
                    gson.fromJson(local.data, PokemonGeneracionResult::class.java)
                }
            }
        } catch (e: Exception) {
            Log.d("DEBUG", "Error en sincronizacion local/remota de Pokemon: ${e.message}")
            null
        }
    }

    private suspend fun fetchAndSave(idPokemon: Int, idGeneracion: Int, nombrePokemon: String, nombreGeneracion: String): PokemonGeneracionResult? {
        val remoteData = remoteDataSource.verPokemonGeneracion(nombrePokemon, nombreGeneracion)
        if (remoteData != null) {
            val jsonStr = gson.toJson(remoteData)
            val entity = PokemonEntity(
                idPokemon = idPokemon,
                idGeneracion = idGeneracion,
                nombrePokemon = nombrePokemon,
                nombreGeneracion = nombreGeneracion,
                fecha = System.currentTimeMillis(),
                data = jsonStr
            )
            pokemonDao.insert(entity)
            return remoteData
        } else {
            Log.d("DEBUG", "Fallo al consumir remotamente Pokemon: $nombrePokemon")
        }
        return null
    }

    private suspend fun fetchAndSave(nombrePokemon: String, nombreGeneracion: String): PokemonGeneracionResult? {
        val remoteData = remoteDataSource.verPokemonGeneracion(nombrePokemon, nombreGeneracion)
        if (remoteData != null) {
            val jsonStr = gson.toJson(remoteData)
            val idGenReal = GenerationConstants.getId(nombreGeneracion) ?: 1
            val entity = PokemonEntity(
                idPokemon = remoteData.id,
                idGeneracion = idGenReal,
                nombrePokemon = nombrePokemon,
                nombreGeneracion = nombreGeneracion,
                fecha = System.currentTimeMillis(),
                data = jsonStr
            )
            pokemonDao.insert(entity)
            return remoteData
        } else {
            Log.d("DEBUG", "Fallo al consumir remotamente Pokemon: $nombrePokemon")
        }
        return null
    }

    override suspend fun deleteId(idPokemon: Int, idGeneracion: Int) {
        try {
            pokemonDao.deleteId(idPokemon, idGeneracion)
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al eliminar Pokemon por ID: ${e.message}")
        }
    }

    override suspend fun deleteAll() {
        try {
            pokemonDao.deleteAll()
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al eliminar todos los pokemon: ${e.message}")
        }
    }

    override suspend fun getSizeBytes(): Long {
        return try {
            pokemonDao.getSizeBytes()
        } catch (e: Exception) {
            Log.d("DEBUG", "Fallo al obtener bytes de Pokemon: ${e.message}")
            0L
        }
    }
}