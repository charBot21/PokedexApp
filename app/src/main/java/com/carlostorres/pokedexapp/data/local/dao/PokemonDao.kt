package com.carlostorres.pokedexapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.carlostorres.pokedexapp.data.local.entity.PokemonDetailsEntity

/**
 * Data Access Object for the Pokemon details table.
 */
@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateDetails(details: PokemonDetailsEntity)

    @Query("SELECT * FROM pokemon_details WHERE name = :name")
    suspend fun getPokemonDetailsByName(name: String): PokemonDetailsEntity?
}