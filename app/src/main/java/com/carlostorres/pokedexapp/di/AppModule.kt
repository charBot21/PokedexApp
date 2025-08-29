package com.carlostorres.pokedexapp.di

import android.app.Application
import androidx.room.Room
import com.carlostorres.pokedexapp.data.local.PokedexDatabase
import com.carlostorres.pokedexapp.data.local.dao.PokemonDao
import com.carlostorres.pokedexapp.data.remote.api.PokeApiService
import com.carlostorres.pokedexapp.data.repository.PokemonRepositoryImpl
import com.carlostorres.pokedexapp.domain.repository.PokemonRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Hilt module to provide application-level dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    /**
     * Provides a singleton instance of the Room database.
     * @param app The application context provided by Hilt.
     * @return The PokedexDatabase instance.
     */
    @Provides
    @Singleton
    fun providePokedexDatabase(app: Application): PokedexDatabase {
        return Room.databaseBuilder(
            app,
            PokedexDatabase::class.java,
            "pokedex_database"
        ).build()
    }

    /**
     * Provides a singleton instance of the PokemonDao.
     * @param database The PokedexDatabase instance provided by Hilt.
     * @return The PokemonDao instance.
     */
    @Provides
    @Singleton
    fun providePokemonDao(database: PokedexDatabase): PokemonDao {
        return database.pokemonDao()
    }

    /**
     * Provides a singleton instance of Moshi for JSON serialization.
     * @return The Moshi instance.
     */
    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    /**
     * Provides a singleton instance of the Retrofit API service.
     * @param moshi The Moshi instance provided by Hilt.
     * @return The PokeApiService instance.
     */
    @Provides
    @Singleton
    fun providePokeApiService(moshi: Moshi): PokeApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(PokeApiService::class.java)
    }

    /**
     * Provides a singleton instance of the PokemonRepository implementation.
     * Note: Since PokemonRepositoryImpl has an @Inject constructor, Hilt already knows
     * how to create it. This @Provides function is explicit but you could also
     * use @Binds in a separate module to bind the interface to the implementation.
     *
     * @param apiService The PokeApiService instance provided by Hilt.
     * @param dao The PokemonDao instance provided by Hilt.
     * @return An implementation of PokemonRepository.
     */
    @Provides
    @Singleton
    fun providePokemonRepository(apiService: PokeApiService, dao: PokemonDao): PokemonRepository {
        return PokemonRepositoryImpl(apiService, dao)
    }
}