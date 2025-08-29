package com.carlostorres.pokedexapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.carlostorres.pokedexapp.ui.detail.PokemonDetailScreen
import com.carlostorres.pokedexapp.ui.list.PokemonListScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.PokemonList.route
    ) {
        composable(route = Screen.PokemonList.route) {
            PokemonListScreen(navController = navController)
        }
        composable(
            route = Screen.PokemonDetail.route,
            arguments = listOf(navArgument("pokemonName") { type = NavType.StringType })
        ) {
            PokemonDetailScreen(navController = navController)
        }
    }
}