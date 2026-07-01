package com.unsa.pokeayuda.ui.navigation

sealed class NavRoutes(val route: String) {
    data object MyPokemon : NavRoutes("my_pokemon")
    data object PokemonDetail : NavRoutes("pokemon_detail/{pokemonId}") {
        fun createRoute(pokemonId: Int): String {
            return "pokemon_detail/$pokemonId"
        }
    }
    data object Settings : NavRoutes("settings")
    data object BattleAnalysis : NavRoutes("battle_analysis")
}