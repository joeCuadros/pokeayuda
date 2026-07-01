package com.unsa.pokeayuda.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

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
            Text("My Pokemon - no implementada")
        }

        composable(NavRoutes.PokemonDetail.route) {
            Text("Pokemon Detail - no implementada")
        }

        composable(NavRoutes.Settings.route) {
            Text("Settings - no implementada")
        }

        composable(NavRoutes.BattleAnalysis.route) {
            Text("Battle Analysis - no implementada")
        }
    }
}