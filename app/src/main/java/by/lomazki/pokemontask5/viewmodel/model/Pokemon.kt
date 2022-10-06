package by.lomazki.pokemontask5.viewmodel.model

import by.lomazki.pokemontask5.constants.Constants.AVATAR_URL


data class Pokemon(
    var id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    var url: String = "",
    var avatarUrl: String = ""
) {
    init {
        this.avatarUrl = AVATAR_URL.format(id)
    }

    fun toPokemonEntity(pokemon: Pokemon): PokemonEntity {
        val url = AVATAR_URL.format(pokemon.id)
        return PokemonEntity(
            pokemon.id,
            pokemon.name,
            pokemon.height,
            pokemon.weight,
            pokemon.url,
            url
        )
    }
}