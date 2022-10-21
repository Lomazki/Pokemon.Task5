package by.lomazki.pokemontask5.extentions

import by.lomazki.pokemontask5.data.model.PokemonFullEntity

fun PokemonFullEntity.addFavourites(): PokemonFullEntity {
    return PokemonFullEntity(
        id, name, height, weight, url, avatarUrl, true
    )
}

fun PokemonFullEntity.removeFavourites(): PokemonFullEntity {
    return PokemonFullEntity(
        id, name, height, weight, url, avatarUrl, false
    )
}