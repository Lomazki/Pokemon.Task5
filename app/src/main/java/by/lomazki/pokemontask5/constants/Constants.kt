package by.lomazki.pokemontask5.constants

object Constants {

    const val BASE_URL = "https://pokeapi.co/api/v2/"
    const val AVATAR_URL ="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/%d.png"
    const val ITEMS_TO_LOAD = 25            // при достижении скольки item начнется дозагрузка следующей страницы
    const val PAGE_SIZE = 40                // количество item на странице (@Query ("limit"))
    const val WEIGHT = "Weight:  %s"
    const val HEIGHT = "Height:  %s"
}
