package by.lomazki.pokemontask5.data.mapper

import by.lomazki.pokemontask5.constants.Constants.AVATAR_URL
import by.lomazki.pokemontask5.data.model.PokemonFullDTO
import by.lomazki.pokemontask5.data.model.PokemonFullEntity
import by.lomazki.pokemontask5.data.model.PokemonShortDTO
import by.lomazki.pokemontask5.data.model.PokemonShortEntity

fun List<PokemonShortDTO>.toDomainModels(): List<PokemonShortEntity> =
    map { it.toShortDomain() }

fun PokemonShortDTO.toShortDomain(): PokemonShortEntity {
    return PokemonShortEntity(name, url)
}

fun PokemonShortDTO.toShortData(): PokemonShortEntity {
    return PokemonShortEntity(name, url)
}

fun PokemonShortEntity.toPokemonShortDTO(): PokemonShortDTO {
    return PokemonShortDTO(name, url)
}

fun PokemonFullDTO.toPokemonFullEntity(): PokemonFullEntity {
    val avatarUrl = AVATAR_URL.format(id)
    return PokemonFullEntity(id, name, weight, height, species.url, avatarUrl, favorite = false)
}
