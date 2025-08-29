package com.carlostorres.pokedexapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters as RoomTypeConverters // Alias to avoid name clash
import com.carlostorres.pokedexapp.data.local.dao.PokemonDao
import com.carlostorres.pokedexapp.data.local.entity.PokemonDetailsEntity

/**
 * The Room database for the application.
 * It contains the pokemon_details table and uses TypeConverters for complex objects.
 */
@Database(
    entities = [PokemonDetailsEntity::class],
    version = 1,
    exportSchema = false
)
@RoomTypeConverters(com.carlostorres.pokedexapp.data.local.TypeConverters::class)
abstract class PokedexDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}