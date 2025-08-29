package com.carlostorres.pokedexapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents the detailed information of a Pokemon stored in the local database.
 * This entity is used for caching purposes.
 */
@Entity(tableName = "pokemon_details")
data class PokemonDetailsEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val height: Int, // in decimetres
    val weight: Int, // in hectograms
    val imageUrl: String,
    val types: List<String>,
    val stats: List<PokemonStatEntity>
)

/**
 * Represents a single stat for a Pokemon within the entity.
 * This is not an entity itself but a data class to be converted to/from JSON.
 */
data class PokemonStatEntity(
    val name: String,
    val value: Int
)
