package by.lomazki.pokemontask5.presentation.ui.listpokemon

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.lomazki.pokemontask5.data.mapper.toPokemonShortDTO
import by.lomazki.pokemontask5.data.model.pokemonshort.PokemonShortEntity
import by.lomazki.pokemontask5.domain.repository.Repository
import by.lomazki.pokemontask5.presentation.models.Lce
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PokemonViewModel(
    private val repository: Repository
) : ViewModel() {

    private var isLoading = false
    private var currentPage = 0
    private val queryFlow = MutableStateFlow("")

    private var lceFlow = MutableSharedFlow<Lce<List<PokemonShortEntity>>>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    private var currentPokemonShortFlow = MutableStateFlow(emptyList<PokemonShortEntity>())

    init {
        viewModelScope.launch {
            currentPokemonShortFlow.value = repository.getPokemonShortList().getOrDefault(emptyList())
        }
    }

    val currentLceFlow: Flow<Lce<List<PokemonShortEntity>>> = combine(
        queryFlow, lceFlow
            .onEach { isLoading = true }
            .map {
                when (it) {
                    is Lce.ContentPokemon -> { currentPage = 1 }
                    is Lce.Error -> { /* TODO брось ошибку */ }
                    Lce.Loading -> { currentPage++ }
                }
                repository.getPokemonShortList(limit = getLimitPageSize())
            }
    ) { query, result ->
        result
            .fold(
                onSuccess = { listPokeShort ->
                    Log.d("LoadingDB", "3")
                    isLoading = false
                    repository.insertPokemonShortList(listPokeShort.map { it.toPokemonShortDTO() })
                    Lce.ContentPokemon(currentPokemonShortFlow
                        .value
                        .filter { it.name.contains(query, ignoreCase = true) }
                    )
                },
                onFailure = { Lce.Error(it) }
            )
    }.onStart {
        Log.d("LoadingDB", "1")
        emit(
            Lce.ContentPokemon(
                repository.getSavedPokemonShortList()
            )
        )
        Log.d("LoadingDB", "2")
    }.shareIn(
            viewModelScope,
            SharingStarted.Eagerly,
            replay = 1
        )

    private fun getLimitPageSize(): Int {
        return currentPage * PAGE_SIZE
    }

    fun onQueryChanged(query: String) {
        queryFlow.value = query
    }

    fun onRefreshedPokemons() {
        lceFlow.tryEmit(Lce.ContentPokemon(emptyList()))
    }

    fun onLoadMore() {      // TODO не работает
        if (!isLoading) {
            lceFlow
                .tryEmit(Lce.Loading)
        }
    }
}
private const val PAGE_SIZE = 40    // количество item на странице (@Query ("limit"))