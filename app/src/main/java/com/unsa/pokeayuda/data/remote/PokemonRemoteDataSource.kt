package com.unsa.pokeayuda.data.remote

import com.unsa.pokeayuda.data.remote.model.ability.AbilityGeneracionResult
import com.unsa.pokeayuda.data.remote.model.pokemon.AbilitySlotDto
import com.unsa.pokeayuda.data.remote.model.evolution.ChainNodeDto
import com.unsa.pokeayuda.data.remote.model.evolution.EvolutionDetailResult
import com.unsa.pokeayuda.data.remote.model.evolution.EvolutionNodeResult
import com.unsa.pokeayuda.data.remote.model.move.MoveFinalDto
import com.unsa.pokeayuda.data.remote.model.move.MoveGeneracionResult
import com.unsa.pokeayuda.data.remote.model.move.MoveMetaResult
import com.unsa.pokeayuda.data.remote.model.pokemon.PokemonDetailResponse
import com.unsa.pokeayuda.data.remote.model.pokemon.PokemonGeneracionResult
import com.unsa.pokeayuda.data.remote.model.pokemon.StatSlotDto
import com.unsa.pokeayuda.data.remote.model.type.TypeGeneracionResult
import com.unsa.pokeayuda.data.remote.model.pokemon.TypeSlotDto
import com.unsa.pokeayuda.utils.constants.GenerationConstants
import java.lang.Exception

class PokemonRemoteDataSource(
    private val apiService: PokeApiService
) {
    suspend fun getPokemonNamesByGeneration(generationName: String): List<String> {
        return try {
            val response = apiService.getPokemonByGeneration(generationName)
            response.pokemonSpecies.map { it.name }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun verPokemonGeneracion(namePokemon: String, nameGeneration: String): PokemonGeneracionResult? {
        return try {
            val data = apiService.getPokemonDetail(namePokemon)
            PokemonGeneracionResult(
                id = data.id,
                name = data.name,
                sprites = data.sprites.frontDefault,
                abilities = obtenerHabilidadesPorGeneracion(data, nameGeneration),
                stats = obtenerStatsPorGeneracion(data, nameGeneration),
                types = obtenerTiposPorGeneracion(data, nameGeneration),
                moves = obtenerMovesPorGeneracion(data, nameGeneration)
            )
        } catch (e: Exception) {
            null
        }
    }

    private fun obtenerHabilidadesPorGeneracion(data: PokemonDetailResponse, generation: String): List<AbilitySlotDto> {
        if (generation == "generation-i" || generation == "generation-ii") {
            return emptyList()
        }
        val abilities = data.abilities.map { it.copy() }.toMutableList()
        val targetGenId = GenerationConstants.getId(generation) ?: return abilities
        for (cambio in data.pastAbilities) {
            val genCambioId = GenerationConstants.getId(cambio.generation.name) ?: continue
            if (genCambioId >= targetGenId) {
                for (habilidad in cambio.abilities) {
                    val index = abilities.indexOfFirst { it.slot == habilidad.slot }
                    if (index != -1) {
                        abilities[index] = habilidad.copy()
                    }
                }
            }
        }
        return abilities.filter { it.ability != null }
    }

    private fun obtenerStatsPorGeneracion(data: PokemonDetailResponse, generation: String): List<StatSlotDto> {
        var stats = data.stats.map { it.copy() }.toMutableList()
        val targetGenId = GenerationConstants.getId(generation) ?: return stats
        for (cambio in data.pastStats) {
            val genCambioId = GenerationConstants.getId(cambio.generation.name) ?: continue
            if (genCambioId >= targetGenId) {
                for (stat in cambio.stats) {
                    val index = stats.indexOfFirst { it.stat.name == stat.stat.name }
                    if (index != -1) {
                        stats[index] = stat.copy()
                    } else {
                        stats.add(stat.copy())
                    }
                }
            }
        }
        return if (generation == "generation-i") {
            stats.filter { it.stat.name != "special-attack" && it.stat.name != "special-defense" }
        } else {
            stats.filter { it.stat.name != "special" }
        }
    }

    private fun obtenerTiposPorGeneracion(data: PokemonDetailResponse, generation: String): List<TypeSlotDto> {
        var types = data.types.map { it.copy() }
        val targetGenId = GenerationConstants.getId(generation) ?: return types

        for (cambio in data.pastTypes) {
            val genCambioId = GenerationConstants.getId(cambio.generation.name) ?: continue
            if (genCambioId >= targetGenId) {
                types = cambio.types.map { it.copy() }
            }
        }
        return types.sortedBy { it.slot }
    }

    private fun obtenerMovesPorGeneracion(data: PokemonDetailResponse, generation: String): Map<String, Map<String, List<MoveFinalDto>>> {
        val juegosValidos = GenerationConstants.getGames(generation)
        val resultado = mutableMapOf<String, MutableMap<String, MutableList<MoveFinalDto>>>()

        for (moveSlot in data.moves) {
            val moveName = moveSlot.move.name
            val moveUrl = moveSlot.move.url

            for (vg in moveSlot.versionGroupDetails) {
                val juego = vg.versionGroup.name
                val metodo = vg.moveLearnMethod.name

                if (!juegosValidos.contains(juego)) continue

                val juegoMap = resultado.getOrPut(juego) { mutableMapOf() }
                val metodoList = juegoMap.getOrPut(metodo) { mutableListOf() }

                metodoList.add(
                    MoveFinalDto(
                        name = moveName,
                        level = vg.levelLearnedAt,
                        url = moveUrl
                    )
                )
            }
        }

        for ((_, juegoMap) in resultado) {
            for ((_, metodoList) in juegoMap) {
                metodoList.sortBy { it.level }
            }
        }
        return resultado
    }

    suspend fun obtenerCadenaEvolutiva(pokemonName: String): EvolutionNodeResult? {
        return try {
            val species = apiService.getPokemonSpecies(pokemonName)
            val idEvolution = species.evolutionChain.url
                .split("/")
                .filter { it.isNotEmpty() }
                .lastOrNull() ?: ""

            val evoData = apiService.getEvolutionChain(species.evolutionChain.url)
            fun recorrer(nodo: ChainNodeDto): EvolutionNodeResult {
                return EvolutionNodeResult(
                    name = nodo.species.name,
                    idEvolution = idEvolution,
                    evolution_details = nodo.evolutionDetails.map { d ->
                        EvolutionDetailResult(
                            method = d.trigger?.name,
                            level = d.minLevel,
                            item = d.item?.name,
                            version_group = d.versionGroup?.name
                        )
                    },
                    evolves_to = nodo.evolvesTo.map { evo -> recorrer(evo) }
                )
            }
            recorrer(evoData.chain)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun obtenerMasInfoAtaque(nameAtaque: String, generation: String): MoveGeneracionResult? {
        return try {
            val data = apiService.getMoveDetail(nameAtaque)
            val idGeneracion = GenerationConstants.getId(generation) ?: return null

            // Nombre en español
            val nameEs = data.names.find { it.language.name == "es" || it.language.name == "es-419" }?.name
                ?: data.name

            var accuracy = data.accuracy
            var power = data.power
            var pp = data.pp
            var effectChance = data.effectChance
            var type = data.type.name
            var effect = data.effectEntries.find { it.language.name == "en" }?.shortEffect

            // Past values (igual que JS)
            for (cambio in data.pastValues) {
                val idCambio = GenerationConstants.findByGame(cambio.versionGroup.name)?.id
                if (idCambio != null && idCambio > idGeneracion) {
                    cambio.accuracy?.let { accuracy = it }
                    cambio.power?.let { power = it }
                    cambio.pp?.let { pp = it }
                    cambio.effectChance?.let { effectChance = it }
                    cambio.type?.let { type = it.name }
                }
            }

            // Effect changes
            for (cambio in data.effectChanges) {
                val idCambio = GenerationConstants.findByGame(cambio.versionGroup.name)?.id
                if (idCambio != null && idCambio > idGeneracion) {
                    val efecto = cambio.effectEntries.find { it.language.name == "en" }
                    if (efecto != null) {
                        effect = efecto.effect
                    }
                }
            }

            // Flavor texts en español
            val nameFlavorText = data.flavorTextEntries
                .filter { it.language.name == "es" || it.language.name == "es-419" }
                .map { entry ->
                    entry.flavorText
                        .replace("\n", " ")
                        .replace("\\s+".toRegex(), " ")
                        .trim()
                }
                .distinct()

            MoveGeneracionResult(
                id = data.id,
                name = data.name,
                nameEs = nameEs,
                accuracy = accuracy,
                power = power,
                pp = pp,
                priority = data.priority,
                effectChance = effectChance,
                damageClass = data.damageClass.name,
                target = data.target.name,
                type = type,
                meta = MoveMetaResult(
                    ailment = data.meta?.ailment?.name ?: "none",
                    ailmentChance = data.meta?.ailmentChance ?: 0,
                    category = data.meta?.category?.name ?: "none",
                    critRate = data.meta?.critRate ?: 0,
                    drain = data.meta?.drain ?: 0,
                    flinchChance = data.meta?.flinchChance ?: 0,
                    healing = data.meta?.healing ?: 0,
                    maxHits = data.meta?.maxHits,
                    maxTurns = data.meta?.maxTurns,
                    minHits = data.meta?.minHits,
                    minTurns = data.meta?.minTurns,
                    statChance = data.meta?.statChance ?: 0
                ),
                statChanges = data.statChanges.map { it.copy() },
                effect = effect,
                nameFlavorText = nameFlavorText
            )
        } catch (e: Exception) {
            null
        }
    }

    suspend fun obtenerInfoHabilidad(nameAbility: String, generation: String): AbilityGeneracionResult? {
        return try {
            val data = apiService.getAbilityDetail(nameAbility)
            val idGeneracion = GenerationConstants.getId(generation) ?: return null

            // Effect actual
            var effect: String? = null
            var shortEffect: String? = null

            val effectActual = data.effectEntries.find { it.language.name == "en" }
            if (effectActual != null) {
                effect = effectActual.effect
                shortEffect = effectActual.shortEffect
            }

            // Aplicar cambios de generaciones posteriores (igual que JS)
            for (cambio in data.effectChanges) {
                val idCambio = GenerationConstants.findByGame(cambio.versionGroup.name)?.id
                if (idCambio != null && idCambio > idGeneracion) {
                    val efectoEn = cambio.effectEntries.find { it.language.name == "en" }
                    if (efectoEn != null) {
                        effect = efectoEn.effect
                        shortEffect = efectoEn.shortEffect
                    }
                }
            }

            // Flavor texts en español según generación
            val flavorText = data.flavorTextEntries
                .filter { entry ->
                    val idCambio = GenerationConstants.findByGame(entry.versionGroup.name)?.id
                    (entry.language.name == "es" || entry.language.name == "es-419") && idCambio != null
                }
                .map { entry ->
                    entry.flavorText
                        .replace("\n", " ")
                        .replace("\\s+".toRegex(), " ")
                        .trim()
                }
                .distinct()

            // Nombre en español
            val nameEs = data.names.find { it.language.name == "es" || it.language.name == "es-419" }?.name
                ?: data.name

            AbilityGeneracionResult(
                id = data.id,
                name = data.name,
                nameEs = nameEs,
                effect = effect,
                shortEffect = shortEffect,
                flavorText = flavorText
            )
        } catch (e: Exception) {
            null
        }
    }

    suspend fun obtenerInfoTipo(nombreOId: String, generation: String): TypeGeneracionResult? {
        return try {
            val data = apiService.getTypeDetail(nombreOId)
            val idGeneracion = GenerationConstants.getId(generation) ?: return null
            var relaciones = data.damageRelations.deepCopy()
            for (cambio in data.pastDamageRelations) {
                val idCambio = GenerationConstants.getId(cambio.generation.name)
                if (idCambio != null && idCambio >= idGeneracion) {
                    relaciones = cambio.damageRelations.deepCopy()
                }
            }
            val nameEs = data.names.find { it.language.name == "es" }?.name ?: data.name
            TypeGeneracionResult(
                id = data.id,
                name = data.name,
                nameEs = nameEs,
                damageRelations = relaciones
            )
        } catch (e: Exception) {
            null
        }
    }
}