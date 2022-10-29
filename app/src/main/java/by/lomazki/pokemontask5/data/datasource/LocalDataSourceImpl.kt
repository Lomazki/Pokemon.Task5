package by.lomazki.pokemontask5.data.datasource

import by.lomazki.pokemontask5.data.database.PokemonDatabase
import by.lomazki.pokemontask5.data.model.pokemonfull.PokemonFullEntity
import by.lomazki.pokemontask5.data.model.pokemonshort.PokemonShortEntity
import by.lomazki.pokemontask5.domain.datasource.LocalDataSource

class LocalDataSourceImpl(
    private val database: PokemonDatabase
) : LocalDataSource {

    override suspend fun getPokemonShortListRoom(): Result<List<PokemonShortEntity>> {
        return runCatching {
            database.pokemonShortDao().getPokemonsShortEntity()
        }
    }

    override suspend fun getPokemonFullListRoom(): List<PokemonFullEntity> {
        return database.pokemonFullDao().getPokemonFullEntityList()
    }

    override suspend fun insertPokemonShortListRoom(pokemons: List<PokemonShortEntity>) {
        database.pokemonShortDao().insertAll(pokemons)
    }

    override suspend fun insertPokemonFullRoom(pokemonFull: PokemonFullEntity) {
        database.pokemonFullDao().insert(pokemonFull)
    }

    override suspend fun clearDataBases() {
        database.pokemonFullDao().clear()
        database.pokemonShortDao().clear()
    }

    override suspend fun deletePokemonShort(namePokemon: String) {
        val pokeForDel =
            database.pokemonShortDao().getPokemonsShortEntity()
                .first { it.name == namePokemon }

        database
            .pokemonShortDao()
            .deletePokemonShort(pokeForDel)
    }

    override suspend fun deletePokemonFull(pokemonFull: PokemonFullEntity) {
        database.pokemonFullDao().deletePokemonFull(pokemonFull)
    }
}
