package by.lomazki.pokemontask5.presentation.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.lomazki.pokemontask5.data.model.PokemonFullEntity
import by.lomazki.pokemontask5.domain.repository.Repository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: Repository
) : ViewModel() {

    // TODO не забудь отфильтровать базу! выводи только избранных
    val favPokeList = MutableStateFlow(emptyList<PokemonFullEntity>())

    init {
        viewModelScope.launch {
            favPokeList.value = repository.getListPokemonFull()
                .filter { it.favorite }
        }
    }

    fun getListFlow(): Flow<List<PokemonFullEntity>> {
        return favPokeList
    }
}