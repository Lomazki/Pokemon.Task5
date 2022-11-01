package by.lomazki.pokemontask5.presentation.ui.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.lomazki.pokemontask5.data.model.pokemonfull.PokemonFullEntity
import by.lomazki.pokemontask5.domain.repository.Repository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: Repository
) : ViewModel() {

    // TODO не забудь отфильтровать базу! выводи только избранных
    val favPokeList = MutableStateFlow(emptyList<PokemonFullEntity>())

    init {
        viewModelScope.launch {
            updateFavPokeList()
        }
    }

    fun onSwipeRight(position: Int) {
        viewModelScope.launch {
            val userForDelete = favPokeList.value[position]
            repository.deletePokemon(userForDelete.name)
            updateFavPokeList()
        }
    }

    fun getListFlow(): Flow<List<PokemonFullEntity>> {
        return favPokeList
    }

    private suspend fun updateFavPokeList(){
        favPokeList.value = repository.getListPokemonFull()
            .filter { it.favorite }
    }
}