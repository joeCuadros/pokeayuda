package com.unsa.pokeayuda.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.unsa.pokeayuda.ui.screens.pokemon.PokemonScreen
import com.unsa.pokeayuda.ui.screens.settings.SettingsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.MyPokemon.route,
        modifier = modifier
    ) {

        composable(NavRoutes.MyPokemon.route) {
            PokemonScreen(
                onNavigateToDetail = { pokemonId ->
                    navController.navigate(NavRoutes.PokemonDetail.createRoute(pokemonId))
                }
            )
        }

        composable(NavRoutes.PokemonDetail.route) {
            Text("Pokemon Detail - no implementada")
        }

        composable(NavRoutes.Settings.route) {
            SettingsScreen()
        }

        composable(NavRoutes.BattleAnalysis.route) {
            Text("Battle Analysis - no implementada")
        }
    }
}