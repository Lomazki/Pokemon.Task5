package by.lomazki.pokemontask5.data.datasource

import by.lomazki.pokemontask5.data.api.PokemonApi
import by.lomazki.pokemontask5.data.model.PokemonFullDTO
import by.lomazki.pokemontask5.data.model.PokemonShortDTO
import by.lomazki.pokemontask5.domain.datasource.RemoteDataSource

class RemoteDataSourceImpl(
    private val pokemonApi: PokemonApi
) : RemoteDataSource {

    override suspend fun getPokemonListApi(limit: Int, offset: Int): Result<List<PokemonShortDTO>> {
        return runCatching {
            pokemonApi.getGeneralRequest(limit, offset).pokemonShortListDTO
        }
    }

    override suspend fun getPokemonApi(name: String): Result<PokemonFullDTO> {
        return runCatching {
            pokemonApi.getPokemonDTO(name)
        }
    }
}
