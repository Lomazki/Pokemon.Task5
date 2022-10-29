package by.lomazki.pokemontask5.data.model.pokemonfull

// Pokemon приходящий со второго запроса
data class PokemonFullDTO(
    val id: Int,
    val name: String,
    val weight: Int,
    val height: Int,
    val species: Species
)

data class Species(
    val name: String,
    val url: String
)
