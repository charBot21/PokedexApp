package com.carlostorres.pokedexapp.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlostorres.pokedexapp.domain.usecase.GetPokemonDetailsUseCase
import com.carlostorres.pokedexapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Pokemon Detail screen.
 * @param getPokemonDetailsUseCase The use case to fetch details for a specific Pokemon.
 * @param savedStateHandle Handle to access navigation arguments.
 */
@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val getPokemonDetailsUseCase: GetPokemonDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<PokemonDetailUiState>(PokemonDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        val pokemonName = savedStateHandle.get<String>("pokemonName")
        if (pokemonName != null) {
            loadPokemonDetails(pokemonName)
        } else {
            _uiState.value = PokemonDetailUiState.Error("Pokemon not found.")
        }
    }

    private fun loadPokemonDetails(name: String) {
        viewModelScope.launch {
            getPokemonDetailsUseCase(name).collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.value = PokemonDetailUiState.Loading
                    }
                    is Resource.Success -> {
                        resource.data?.let { details ->
                            _uiState.value = PokemonDetailUiState.Success(details)
                        } ?: run {
                            _uiState.value = PokemonDetailUiState.Error("Failed to load details.")
                        }
                    }
                    is Resource.Error -> {
                        _uiState.value = PokemonDetailUiState.Error(resource.message ?: "An unknown error occurred.")
                    }
                }
            }
        }
    }
}