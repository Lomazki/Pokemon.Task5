package by.lomazki.pokemontask5.viewmodel

import androidx.lifecycle.ViewModel
import by.lomazki.pokemontask5.model.Repository
import by.lomazki.pokemontask5.viewmodel.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class DetailsViewModel(
    private val repository: Repository,
    private val namePokemon: String
) : ViewModel() {

    private val _currentPokemonFlow = MutableStateFlow(Pokemon(0, "", 0, 0))
    val currentPokemonFlow: Flow<Pokemon> = _currentPokemonFlow

    suspend fun loadPokemon() {
        val loadedPokemon = repository.getPokemonApi(namePokemon)

        _currentPokemonFlow.value = Pokemon(
            loadedPokemon.id,
            loadedPokemon.name,
            loadedPokemon.height,
            loadedPokemon.weight
        )
    }
}