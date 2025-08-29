package com.carlostorres.pokedexapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.carlostorres.pokedexapp.data.local.dao.PokemonDao
import com.carlostorres.pokedexapp.data.mappers.toDomain
import com.carlostorres.pokedexapp.data.mappers.toEntity
import com.carlostorres.pokedexapp.data.remote.PokemonPagingSource
import com.carlostorres.pokedexapp.data.remote.api.PokeApiService
import com.carlostorres.pokedexapp.domain.model.Pokemon
import com.carlostorres.pokedexapp.domain.model.PokemonDetails
import com.carlostorres.pokedexapp.domain.repository.PokemonRepository
import com.carlostorres.pokedexapp.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of the PokemonRepository.
 * This class is the single source of truth for Pokemon data. It handles
 * fetching data from the network and caching it in the local database.
 * @param apiService The service for network requests.
 * @param dao The Data Access Object for database operations.
 */
@Singleton
class PokemonRepositoryImpl @Inject constructor(
    private val apiService: PokeApiService,
    private val dao: PokemonDao
) : PokemonRepository {

    override fun getPokemonList(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PokemonPagingSource(apiService) }
        ).flow
    }

    override fun getPokemonDetails(name: String): Flow<Resource<PokemonDetails>> = flow {
        emit(Resource.Loading())

        val cachedDetails = dao.getPokemonDetailsByName(name)
        if (cachedDetails != null) {
            emit(Resource.Success(cachedDetails.toDomain()))
        }

        try {
            val remoteDetails = apiService.getPokemonDetails(name)
            dao.insertOrUpdateDetails(remoteDetails.toEntity())

            val newCachedDetails = dao.getPokemonDetailsByName(name)
            if (newCachedDetails != null) {
                emit(Resource.Success(newCachedDetails.toDomain()))
            } else {
                emit(Resource.Error("Failed to load data from cache after fetch."))
            }

        } catch (e: HttpException) {
            emit(Resource.Error(
                message = "Oops, something went wrong! (HTTP)",
                data = cachedDetails?.toDomain()
            ))
        } catch (e: IOException) {
            emit(Resource.Error(
                message = "Couldn't reach server. Check your internet connection.",
                data = cachedDetails?.toDomain()
            ))
        }
    }
}