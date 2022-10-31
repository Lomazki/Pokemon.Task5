package by.lomazki.pokemontask5.data.repository

import by.lomazki.pokemontask5.data.mapper.toDomainModels
import by.lomazki.pokemontask5.data.mapper.toPokemonFullEntity
import by.lomazki.pokemontask5.data.mapper.toShortData
import by.lomazki.pokemontask5.data.model.pokemonfull.PokemonFullEntity
import by.lomazki.pokemontask5.data.model.pokemonshort.PokemonShortDTO
import by.lomazki.pokemontask5.data.model.pokemonshort.PokemonShortEntity
import by.lomazki.pokemontask5.domain.datasource.LocalDataSource
import by.lomazki.pokemontask5.domain.datasource.RemoteDataSource
import by.lomazki.pokemontask5.domain.repository.Repository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.*

// на этом уравне желательно делать проверки и трансформации данных, если что-то пошло не так
class RepositoryImpl(
    private val localDB: LocalDataSource,
    private val network: RemoteDataSource
) : Repository {

    private val pokemonListShortFlow = MutableStateFlow(emptyList<PokemonShortEntity>())
    private val pokemonListFullFlow =
        MutableStateFlow(emptyList<PokemonFullEntity>())

    init {
        flow<Unit> { pokemonListFullFlow.value = localDB.getPokemonFullListRoom() }
            .launchIn(MainScope())
//        flow<Unit>{pokemonListShortFlow.value = localDB.getPokemonShortListRoom()}
    }

    private val curShortList: Flow<List<PokemonShortEntity>> =
        pokemonListShortFlow
            .onStart {  // не работает
                emit(
                    localDB.getPokemonShortListRoom()
                        .getOrDefault(emptyList())
                )
            }
            .map {
                network.getPokemonListApi(100, 200)      // TODO избавься от магических чисел
                    .fold(
                        onSuccess = { it.toDomainModels() },
                        onFailure = { emptyList() }
                    )
            }
            .shareIn(
                scope = MainScope(),
                SharingStarted.Eagerly,
                replay = 1
            )


    override suspend fun getPokemonShortList(limit: Int, offset: Int): List<PokemonShortEntity> {
        return curShortList.first()
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
        return localDB.getPokemonFullListRoom()
    }

    override suspend fun getPokemonFull(name: String): PokemonFullEntity {
        return if (pokemonListFullFlow.value.isEmpty()) {
            pokemonListFullFlow
                .runCatching {
                    network.getPokemonApi(name)
                }
                .fold(
                    onSuccess = {
                        val curPoke = it.getOrThrow().toPokemonFullEntity()
                        localDB.insertPokemonFullRoom(curPoke)
                        curPoke
                    },
                    onFailure = { error(it) }
                )
        } else {
            pokemonListFullFlow.value.first { it.name == name }
        }
    }

    override suspend fun insertPokemonFull(pokemon: PokemonFullEntity) {
        localDB.insertPokemonFullRoom(pokemon)
        pokemonListFullFlow.value = localDB.getPokemonFullListRoom()
    }

    override suspend fun deletePokemon(namePokemon: String) {
        localDB.deletePokemonFull(
            pokemonListFullFlow
                .value
                .first { it.name == namePokemon }
        )
    }

    override suspend fun clearDB() {
        localDB.clearDataBases()
    }
}
