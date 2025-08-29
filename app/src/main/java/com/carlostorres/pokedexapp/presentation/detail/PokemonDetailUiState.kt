package com.carlostorres.pokedexapp.presentation.detail

import com.carlostorres.pokedexapp.domain.model.PokemonDetails

/**
 * Represents the different states for the Pokemon Detail screen UI.
 */
sealed interface PokemonDetailUiState {
    /**
     * The screen is currently loading data.
     */
    object Loading : PokemonDetailUiState

    /**
     * The data has been successfully loaded.
     * @param details The Pokemon details to display.
     */
    data class Success(val details: PokemonDetails) : PokemonDetailUiState

    /**
     * An error occurred while loading data.
     * @param message The error message to display.
     */
    data class Error(val message: String) : PokemonDetailUiState
}