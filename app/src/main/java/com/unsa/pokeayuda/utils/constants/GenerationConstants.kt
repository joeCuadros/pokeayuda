package com.unsa.pokeayuda.utils.constants

object GenerationConstants {

    private val generations = mapOf(
        "generation-i" to GenerationInfo(
            id = 1,
            games = listOf(
                "red-blue",
                "yellow"
            )
        ),
        "generation-ii" to GenerationInfo(
            id = 2,
            games = listOf(
                "gold-silver",
                "crystal"
            )
        ),
        "generation-iii" to GenerationInfo(
            id = 3,
            games = listOf(
                "ruby-sapphire",
                "emerald",
                "firered-leafgreen"
            )
        ),
        "generation-iv" to GenerationInfo(
            id = 4,
            games = listOf(
                "diamond-pearl",
                "platinum",
                "heartgold-soulsilver"
            )
        ),
        "generation-v" to GenerationInfo(
            id = 5,
            games = listOf(
                "black-white",
                "black-2-white-2"
            )
        ),
        "generation-vi" to GenerationInfo(
            id = 6,
            games = listOf(
                "x-y",
                "omega-ruby-alpha-sapphire"
            )
        ),
        "generation-vii" to GenerationInfo(
            id = 7,
            games = listOf(
                "sun-moon",
                "ultra-sun-ultra-moon",
                "lets-go-pikachu-eevee"
            )
        ),
        "generation-viii" to GenerationInfo(
            id = 8,
            games = listOf(
                "sword-shield",
                "the-isle-of-armor",
                "the-crown-tundra",
                "brilliant-diamond-shining-pearl",
                "legends-arceus"
            )
        ),
        "generation-ix" to GenerationInfo(
            id = 9,
            games = listOf(
                "scarlet-violet",
                "teal-mask",
                "indigo-disk"
            )
        )
    )
    fun getId(generation: String): Int? {
        return generations[generation]?.id
    }
    fun getGames(generation: String): List<String> {
        return generations[generation]?.games ?: emptyList()
    }
    fun findByGame(versionGroupName: String): GenerationInfo? {
        return generations.values.firstOrNull {
            versionGroupName in it.games
        }
    }
    fun get(generation: String): GenerationInfo? {
        return generations[generation]
    }
    fun getGenerationNames(): List<String> {
        return generations.keys.toList()
    }
}