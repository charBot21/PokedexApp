package com.carlostorres.pokedexapp.domain.model

/**
 * Represents the detailed information of a Pokemon.
 * This is a core business model, independent of data sources.
 */
data class PokemonDetails(
    val id: Int,
    val name: String,
    val height: Float, // in meters
    val weight: Float, // in kilograms
    val imageUrl: String,
    val types: List<String>,
    val stats: List<PokemonStat>
)

data class PokemonStat(
    val name: String,
    val value: Int
)