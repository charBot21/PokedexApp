package com.carlostorres.pokedexapp.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Tells KSP to generate a Moshi adapter for this class. THIS IS THE FIX.
 */
@JsonClass(generateAdapter = true)
data class PokemonDetailDto(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "height") val height: Int,
    @field:Json(name = "weight") val weight: Int,
    @field:Json(name = "stats") val stats: List<StatDto>,
    @field:Json(name = "types") val types: List<TypeDto>,
    @field:Json(name = "sprites") val sprites: SpritesDto
)

@JsonClass(generateAdapter = true)
data class StatDto(
    @field:Json(name = "base_stat") val baseStat: Int,
    @field:Json(name = "stat") val statInfo: StatInfoDto
)

@JsonClass(generateAdapter = true)
data class StatInfoDto(
    @field:Json(name = "name") val name: String
)

@JsonClass(generateAdapter = true)
data class TypeDto(
    @field:Json(name = "type") val typeInfo: TypeInfoDto
)

@JsonClass(generateAdapter = true)
data class TypeInfoDto(
    @field:Json(name = "name") val name: String
)

@JsonClass(generateAdapter = true)
data class SpritesDto(
    @field:Json(name = "other") val other: OtherSpritesDto?
)

@JsonClass(generateAdapter = true)
data class OtherSpritesDto(
    @field:Json(name = "official-artwork") val officialArtwork: OfficialArtworkDto?
)

@JsonClass(generateAdapter = true)
data class OfficialArtworkDto(
    @field:Json(name = "front_default") val frontDefault: String?
)