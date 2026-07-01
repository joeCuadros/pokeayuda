package com.unsa.pokeayuda.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CatchingPokemon
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SportsKabaddi
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.unsa.pokeayuda.ui.navigation.NavRoutes

@Composable
fun BottomNavigationBar(
    navController: NavHostController
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route

    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == NavRoutes.MyPokemon.route,
            onClick = {
                navController.navigate(NavRoutes.MyPokemon.route)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.CatchingPokemon,
                    contentDescription = null
                )
            },
            label = {
                Text("Pokémon")
            }
        )

        NavigationBarItem(
            selected = currentRoute == NavRoutes.BattleAnalysis.route,
            onClick = {
                navController.navigate(NavRoutes.BattleAnalysis.route)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.SportsKabaddi,
                    contentDescription = null
                )
            },
            label = {
                Text("Combate")
            }
        )

        NavigationBarItem(
            selected = currentRoute == NavRoutes.Settings.route,
            onClick = {
                navController.navigate(NavRoutes.Settings.route)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null
                )
            },
            label = {
                Text("Ajustes")
            }
        )
    }
}