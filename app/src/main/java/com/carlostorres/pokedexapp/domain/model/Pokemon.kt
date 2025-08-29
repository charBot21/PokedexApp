package com.carlostorres.pokedexapp.domain.model

/**
 * Represents a Pokemon in the list view.
 * This is a core business model, independent of data sources.
 */
data class Pokemon(
    val name: String,
    val pokedexNumber: Int,
    val imageUrl: String
)