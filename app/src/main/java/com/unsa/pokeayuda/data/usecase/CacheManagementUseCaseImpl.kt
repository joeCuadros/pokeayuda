package com.unsa.pokeayuda.data.usecase

import com.unsa.pokeayuda.domain.model.CacheSize
import com.unsa.pokeayuda.domain.repository.PokemonRepository
import com.unsa.pokeayuda.domain.repository.GeneracionRepository
import com.unsa.pokeayuda.domain.repository.CadenaEvolutivaRepository
import com.unsa.pokeayuda.domain.repository.AtaqueRepository
import com.unsa.pokeayuda.domain.repository.HabilidadRepository
import com.unsa.pokeayuda.domain.repository.TipoRepository
import com.unsa.pokeayuda.domain.usecase.CacheManagementUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CacheManagementUseCaseImpl @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    private val generacionRepository: GeneracionRepository,
    private val cadenaEvolutivaRepository: CadenaEvolutivaRepository,
    private val ataqueRepository: AtaqueRepository,
    private val habilidadRepository: HabilidadRepository,
    private val tipoRepository: TipoRepository
) : CacheManagementUseCase {

    override suspend fun getPokemonCacheSize(): Long = withContext(Dispatchers.IO) {
        pokemonRepository.getSizeBytes()
    }

    override suspend fun clearPokemonCache() = withContext(Dispatchers.IO) {
        pokemonRepository.deleteAll()
    }

    override suspend fun getGenerationCacheSize(): Long = withContext(Dispatchers.IO) {
        generacionRepository.getSizeBytes()
    }

    override suspend fun clearGenerationCache() = withContext(Dispatchers.IO) {
        generacionRepository.deleteAll()
    }

    override suspend fun getEvolutionChainCacheSize(): Long = withContext(Dispatchers.IO) {
        cadenaEvolutivaRepository.getSizeBytes()
    }

    override suspend fun clearEvolutionChainCache() = withContext(Dispatchers.IO) {
        cadenaEvolutivaRepository.deleteAll()
    }

    override suspend fun getMoveCacheSize(): Long = withContext(Dispatchers.IO) {
        ataqueRepository.getSizeBytes()
    }

    override suspend fun clearMoveCache() = withContext(Dispatchers.IO) {
        ataqueRepository.deleteAll()
    }

    override suspend fun getAbilityCacheSize(): Long = withContext(Dispatchers.IO) {
        habilidadRepository.getSizeBytes()
    }

    override suspend fun clearAbilityCache() = withContext(Dispatchers.IO) {
        habilidadRepository.deleteAll()
    }

    override suspend fun getTypeCacheSize(): Long = withContext(Dispatchers.IO) {
        tipoRepository.getSizeBytes()
    }

    override suspend fun clearTypeCache() = withContext(Dispatchers.IO) {
        tipoRepository.deleteAll()
    }

    override suspend fun getTotalCacheSize(): CacheSize = withContext(Dispatchers.IO) {
        coroutineScope {
            val pokemon = async { pokemonRepository.getSizeBytes() }
            val generation = async { generacionRepository.getSizeBytes() }
            val evolutionChain = async { cadenaEvolutivaRepository.getSizeBytes() }
            val move = async { ataqueRepository.getSizeBytes() }
            val ability = async { habilidadRepository.getSizeBytes() }
            val type = async { tipoRepository.getSizeBytes() }

            CacheSize(
                pokemon = pokemon.await(),
                generation = generation.await(),
                evolutionChain = evolutionChain.await(),
                move = move.await(),
                ability = ability.await(),
                type = type.await()
            )
        }
    }

    override suspend fun clearAllCache() = withContext(Dispatchers.IO) {
        pokemonRepository.deleteAll()
        generacionRepository.deleteAll()
        cadenaEvolutivaRepository.deleteAll()
        ataqueRepository.deleteAll()
        habilidadRepository.deleteAll()
        tipoRepository.deleteAll()
    }
}