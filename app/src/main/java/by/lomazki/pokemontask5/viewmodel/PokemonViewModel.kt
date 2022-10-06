package by.lomazki.pokemontask5.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.lomazki.pokemontask5.constants.Constants.PAGE_SIZE
import by.lomazki.pokemontask5.model.Repository
import by.lomazki.pokemontask5.viewmodel.model.Pokemon
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

class PokemonViewModel(
    private val repository: Repository
) : ViewModel() {

    private var isLoading = false
    private var currentPage = 0
    private val queryFlow = MutableStateFlow("")

    //    private var _lceFlow = MutableStateFlow<Lce<List<Pokemon>>>(Lce.Loading)
    //    private val lceFlow: Flow<Lce<List<Pokemon>>> = _lceFlow.asStateFlow()
    private var lceFlow = MutableSharedFlow<Lce<List<Pokemon>>>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    private var currentListPokemonFlow = MutableStateFlow(emptyList<Pokemon>())

    val currentLceFlow: Flow<Lce<List<Pokemon>>> = combine(
        queryFlow, lceFlow
//            .onStart { emit(Lce.ContentPokemon(repository.getListPokemonRoom())) }    // не работает
            .onEach { isLoading = true }
            .map {
                when (it) {
                    is Lce.ContentPokemon -> { currentPage = 1 }
                    is Lce.Error -> { /* TODO брось ошибку */}
                    Lce.Loading -> { currentPage++ }
                }
                runCatching {
                    repository.getListPokemonApi(getLimitPageSize())
                }
            }
    ) { query, result ->
        result
            .map { listPokemon ->
                setNewListPokemon(listPokemon)
                currentListPokemonFlow
                    .value
                    .filter { it.name.contains(query, ignoreCase = true) }
            }
            .onSuccess {
                repository.replaceRoom(it)
                isLoading = false
            }
            .fold(
                onSuccess = { Lce.ContentPokemon(it) },
                onFailure = { Lce.Error(it) }
            )
    }.onStart {
        emit(Lce.ContentPokemon(repository.getListPokemonRoom()))
    }.shareIn(
        viewModelScope,
        SharingStarted.Eagerly,
        replay = 1
    )

    private fun setNewListPokemon(listPoke: List<Pokemon>): Flow<List<Pokemon>> {
        currentListPokemonFlow.value = listPoke

        return currentListPokemonFlow
            .runningReduce { pokemons, loadPokemons ->
                pokemons + loadPokemons
            }
    }

    private fun getLimitPageSize(): Int {
        return currentPage * PAGE_SIZE
    }

    fun onQueryChanged(query: String) {
        queryFlow.value = query
    }

    fun onRefreshedPokemons() {
        lceFlow.tryEmit(Lce.ContentPokemon(emptyList()))
    }

    fun onLoadMore() {
        if (!isLoading) {
            lceFlow.tryEmit(Lce.Loading)
        }
    }
}
