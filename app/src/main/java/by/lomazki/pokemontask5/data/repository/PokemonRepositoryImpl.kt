package by.lomazki.pokemontask5.data.repository

import android.util.Log
import by.lomazki.pokemontask5.data.mapper.toDomainModels
import by.lomazki.pokemontask5.data.mapper.toPokemonFullEntity
import by.lomazki.pokemontask5.data.mapper.toShortData
import by.lomazki.pokemontask5.data.model.pokemonfull.PokemonFullEntity
import by.lomazki.pokemontask5.data.model.pokemonshort.PokemonShortDTO
import by.lomazki.pokemontask5.data.model.pokemonshort.PokemonShortEntity
import by.lomazki.pokemontask5.domain.datasource.LocalDataSource
import by.lomazki.pokemontask5.domain.datasource.RemoteDataSource
import by.lomazki.pokemontask5.domain.repository.Repository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PokemonRepositoryImpl(
    private val localDB: LocalDataSource,
    private val network: RemoteDataSource
) : Repository {

    private val pokemonListShortFlow = MutableStateFlow(emptyList<PokemonShortEntity>())
    private val pokemonListFullFlow = MutableStateFlow(emptyList<PokemonFullEntity>())

    init {
        MainScope().launch {
            pokemonListFullFlow.value = localDB.getPokemonFullListRoom().getOrDefault(emptyList())
        }
    }

    override suspend fun getPokemonShortList(
        limit: Int,
        offset: Int
    ): Result<List<PokemonShortEntity>> {

        return pokemonListShortFlow
            .map {
                network.getPokemonListApi(limit, offset)
            }
            .stateIn(MainScope())
            .value
            .map { listPoke ->
                listPoke.map { it.toShortData() }
            }
    }

    override suspend fun getSavedPokemonShortList(): List<PokemonShortEntity> {
        return localDB.getPokemonShortListRoom()
            .fold(
                onSuccess = { it },
                onFailure = { emptyList() }
            )
    }

    override suspend fun insertPokemonShortList(pokemonList: List<PokemonShortDTO>) {       // TODO перепиши по-человечески
        val inputList: List<PokemonShortEntity> = pokemonList.toDomainModels()
        val newList = pokemonListShortFlow.value + inputList

        pokemonListShortFlow.emit(newList)
        localDB.insertPokemonShortListRoom(pokemonList.map { it.toShortData() })

//        pokemonListShortFlow      // TODO разберись
//            // не смог запихнуть pokemonList в newPokemonList
//            .runningReduce { currentList, newPokemonList ->
//                currentList + newPokemonList
//            }
//            .onEach {
//                database.insertPokemonShortListRoom(pokemonList.map { it.toPokemonShortEntity() })
//            }
    }

    override suspend fun getListPokemonFull(): List<PokemonFullEntity> {
        return localDB
            .getPokemonFullListRoom()
            .fold(
                onSuccess = { it },
                onFailure = { emptyList() }
            )
    }

    override suspend fun getPokemonFull(name: String): PokemonFullEntity {
        runCatching {
            pokemonListFullFlow.value.first { it.name == name }
        }.fold(
            onSuccess = {
                return it
            },
            onFailure = {
                runCatching {
                    network.getPokemonApi(name)
                }
                    .fold(
                        onSuccess = {
                            val curPoke = it.getOrThrow().toPokemonFullEntity()
                            localDB.insertPokemonFullRoom(curPoke)
                            return curPoke
                        },
                        onFailure = { error(it) }
                    )
            }
        )
    }

    override suspend fun insertPokemonFull(pokemon: PokemonFullEntity) {
        localDB.insertPokemonFullRoom(pokemon)
        pokemonListFullFlow.value =
            localDB
                .getPokemonFullListRoom()
                .fold(
                    onSuccess = { it },
                    onFailure = { emptyList() }
                )
    }

    override suspend fun deletePokemon(namePokemon: String) {
        runCatching {
            localDB.deletePokemonFull(
                pokemonListFullFlow
                    .value
                    .first { it.name == namePokemon }
            )
        }
    }

    override suspend fun clearDB() {
        localDB.clearDataBases()
    }
}
