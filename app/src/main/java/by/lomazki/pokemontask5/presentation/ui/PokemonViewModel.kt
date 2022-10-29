package by.lomazki.pokemontask5.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.lomazki.pokemontask5.constants.Constants.PAGE_SIZE
import by.lomazki.pokemontask5.data.mapper.toPokemonShortDTO
import by.lomazki.pokemontask5.data.model.PokemonShortEntity
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
            currentPokemonShortFlow.value = repository.getPokemonShortList()
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
                runCatching {
                    repository.getPokemonShortList(getLimitPageSize())
                }
            }
    ) { query, result ->
        result
            .fold(
                onSuccess = { listPokeShort ->
                    isLoading = false
                    repository.insertPokemonShortList(listPokeShort.map { it.toPokemonShortDTO() })

                    Lce.ContentPokemon(currentPokemonShortFlow
                        .value
                        .filter { it.name.contains(query, ignoreCase = true) }
                    )
                },
                onFailure = { Lce.Error(it) }
            )
    }.onStart { emit(Lce.ContentPokemon(repository.getPokemonShortList())) }
        .shareIn(
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
            lceFlow.tryEmit(Lce.Loading)
        }
    }
}
