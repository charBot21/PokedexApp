package com.carlostorres.pokedexapp.ui.navigation

sealed class Screen(val route: String) {
    object PokemonList : Screen("pokemon_list_screen")
    object PokemonDetail : Screen("pokemon_detail_screen/{pokemonName}") {
        fun createRoute(pokemonName: String) = "pokemon_detail_screen/$pokemonName"
    }
}