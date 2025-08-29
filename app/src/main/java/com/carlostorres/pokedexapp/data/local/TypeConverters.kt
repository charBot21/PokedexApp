package com.carlostorres.pokedexapp.data.local

import androidx.room.TypeConverter
import com.carlostorres.pokedexapp.data.local.entity.PokemonStatEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Room type converters to allow storing complex data types like lists in the database.
 */
class TypeConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun toStringList(list: List<String>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromPokemonStatEntityList(value: String): List<PokemonStatEntity> {
        val listType = object : TypeToken<List<PokemonStatEntity>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun toPokemonStatEntityList(list: List<PokemonStatEntity>): String {
        return gson.toJson(list)
    }
}