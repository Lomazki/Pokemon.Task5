package by.lomazki.pokemontask5.data.model

import by.lomazki.pokemontask5.data.model.pokemonshort.PokemonShortDTO
import com.google.gson.annotations.SerializedName

// DTO - Data Transfer Object - т.е.  то, что будет прилетать с сервера
data class GeneralResultDTO(
    @SerializedName("results")
    val pokemonShortListDTO: List<PokemonShortDTO>
)
