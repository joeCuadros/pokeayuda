package com.unsa.pokeayuda.domain.repository

import com.unsa.pokeayuda.data.remote.model.pokemon.PokemonGeneracionResult

interface PokemonRepository {
    suspend fun getAll(): List<PokemonGeneracionResult>
    suspend fun getId(idPokemon: Int, idGeneracion: Int, nombrePokemon: String, nombreGeneracion: String): PokemonGeneracionResult?
    suspend fun deleteId(idPokemon: Int, idGeneracion: Int)
    suspend fun deleteAll()
    suspend fun getSizeBytes(): Long
}