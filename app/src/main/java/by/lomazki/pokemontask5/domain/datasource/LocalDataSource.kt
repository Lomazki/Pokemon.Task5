package by.lomazki.pokemontask5.domain.datasource

import by.lomazki.pokemontask5.data.model.PokemonFullEntity
import by.lomazki.pokemontask5.data.model.PokemonShortEntity

interface LocalDataSource {

    suspend fun getPokemonShortListRoom(): Result<List<PokemonShortEntity>>

    suspend fun getPokemonFullListRoom(): List<PokemonFullEntity>

    suspend fun insertPokemonShortListRoom(pokemons: List<PokemonShortEntity>)

    suspend fun insertPokemonFullRoom(pokemonFull: PokemonFullEntity)

    suspend fun clearDataBases()

    suspend fun deletePokemonShort(namePokemon: String)

    suspend fun deletePokemonFull(pokemonFull: PokemonFullEntity)
}
