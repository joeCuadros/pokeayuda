package com.unsa.pokeayuda.data.remote

import com.unsa.pokeayuda.data.remote.model.ability.AbilityDetailResponse
import com.unsa.pokeayuda.data.remote.model.evolution.EvolutionChainResponse
import com.unsa.pokeayuda.data.remote.model.generation.GenerationResponse
import com.unsa.pokeayuda.data.remote.model.move.MoveDetailResponse
import com.unsa.pokeayuda.data.remote.model.pokemon.PokemonDetailResponse
import com.unsa.pokeayuda.data.remote.model.pokemon.PokemonSpeciesResponse
import com.unsa.pokeayuda.data.remote.model.type.TypeDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface PokeApiService {
    @GET("generation/{name}")
    suspend fun getPokemonByGeneration(
        @Path("name") generationName: String
    ): GenerationResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(
        @Path("name") pokemonName: String
    ): PokemonDetailResponse

    @GET("pokemon-species/{name}")
    suspend fun getPokemonSpecies(
        @Path("name") pokemonName: String
    ): PokemonSpeciesResponse

    @GET
    suspend fun getEvolutionChain(
        @Url url: String
    ): EvolutionChainResponse

    @GET("move/{name}")
    suspend fun getMoveDetail(
        @Path("name") moveName: String
    ): MoveDetailResponse

    @GET("ability/{name}")
    suspend fun getAbilityDetail(
        @Path("name") abilityName: String
    ): AbilityDetailResponse

    @GET("type/{nombreOId}")
    suspend fun getTypeDetail(
        @Path("nombreOId") nombreOId: String
    ): TypeDetailResponse
}