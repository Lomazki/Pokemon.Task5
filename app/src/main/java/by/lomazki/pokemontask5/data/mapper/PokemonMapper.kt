package by.lomazki.pokemontask5.data.mapper

import by.lomazki.pokemontask5.data.model.pokemonfull.PokemonFullDTO
import by.lomazki.pokemontask5.data.model.pokemonfull.PokemonFullEntity
import by.lomazki.pokemontask5.data.model.pokemonshort.PokemonShortDTO
import by.lomazki.pokemontask5.data.model.pokemonshort.PokemonShortEntity

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

private const val AVATAR_URL ="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/%d.png"
