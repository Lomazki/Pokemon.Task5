package by.lomazki.pokemontask5.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.lomazki.pokemontask5.data.model.PokemonFullEntity
import by.lomazki.pokemontask5.domain.repository.Repository
import by.lomazki.pokemontask5.extentions.addFavourites
import by.lomazki.pokemontask5.extentions.removeFavourites
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val repository: Repository,
    private val namePokemon: String
) : ViewModel() {

    private var _currentPokemonFullFlow = MutableStateFlow(
        PokemonFullEntity(0, "", 0, 0, "", "", false)
    ) // TODO что-то здесь не так
    val currentPokemonFullFlow: Flow<PokemonFullEntity> = _currentPokemonFullFlow.asStateFlow()

    private var listPokemonFullFlow = MutableStateFlow(emptyList<PokemonFullEntity>())

    init {
        viewModelScope.launch {
            listPokemonFullFlow.value = repository.getListPokemonFull()
        }
    }

    suspend fun loadPokemon() {
        _currentPokemonFullFlow.value = repository.getPokemonFull(namePokemon)
    }

    suspend fun addToFavourites() {
        val newPoke = _currentPokemonFullFlow.value.addFavourites()
        _currentPokemonFullFlow.value = newPoke

        repository.insertPokemonFull(newPoke)
        loadPokemon()
    }

    suspend fun removeFromFavourites() {
        val newPoke = _currentPokemonFullFlow.value.removeFavourites()
        _currentPokemonFullFlow.value = newPoke
        repository.insertPokemonFull(newPoke)
        loadPokemon()
    }

    fun checkIsFavorite(): Boolean {
        return _currentPokemonFullFlow.value.favorite
    }
}
