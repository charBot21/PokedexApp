package com.carlostorres.pokedexapp.data.mappers

import com.carlostorres.pokedexapp.data.local.entity.PokemonDetailsEntity
import com.carlostorres.pokedexapp.data.local.entity.PokemonStatEntity
import com.carlostorres.pokedexapp.data.remote.dto.PokemonDetailDto
import com.carlostorres.pokedexapp.data.remote.dto.PokemonResultDto
import com.carlostorres.pokedexapp.domain.model.Pokemon
import com.carlostorres.pokedexapp.domain.model.PokemonDetails
import com.carlostorres.pokedexapp.domain.model.PokemonStat

/**
 * This file contains extension functions to map data models between
 * the data layer (DTOs, Entities) and the domain layer (business models).
 */
fun getPokedexNumberFromUrl(url: String): Int {
    return url.split("/").filter { it.isNotEmpty() }.last().toInt()
}

fun PokemonResultDto.toDomain(): Pokemon {
    val number = getPokedexNumberFromUrl(this.url)
    val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$number.png"
    return Pokemon(
        name = this.name.replaceFirstChar { it.uppercase() },
        pokedexNumber = number,
        imageUrl = imageUrl
    )
}

fun PokemonDetailDto.toDomain(): PokemonDetails {
    return PokemonDetails(
        id = this.id,
        name = this.name.replaceFirstChar { it.uppercase() },
        height = this.height / 10f, // Convert decimetres to meters
        weight = this.weight / 10f, // Convert hectograms to kilograms
        imageUrl = this.sprites.other?.officialArtwork?.frontDefault ?: "",
        types = this.types.map { it.typeInfo.name.replaceFirstChar { c -> c.uppercase() } },
        stats = this.stats.map {
            PokemonStat(
                name = it.statInfo.name.replaceFirstChar { c -> c.uppercase() },
                value = it.baseStat
            )
        }
    )
}

fun PokemonDetailDto.toEntity(): PokemonDetailsEntity {
    return PokemonDetailsEntity(
        id = this.id,
        name = this.name,
        height = this.height,
        weight = this.weight,
        imageUrl = this.sprites.other?.officialArtwork?.frontDefault ?: "",
        types = this.types.map { it.typeInfo.name },
        stats = this.stats.map {
            PokemonStatEntity(
                name = it.statInfo.name,
                value = it.baseStat
            )
        }
    )
}

fun PokemonDetailsEntity.toDomain(): PokemonDetails {
    return PokemonDetails(
        id = this.id,
        name = this.name.replaceFirstChar { it.uppercase() },
        height = this.height / 10f,
        weight = this.weight / 10f,
        imageUrl = this.imageUrl,
        types = this.types.map { it.replaceFirstChar { c -> c.uppercase() } },
        stats = this.stats.map {
            PokemonStat(
                name = it.name.replaceFirstChar { c -> c.uppercase() },
                value = it.value
            )
        }
    )
}