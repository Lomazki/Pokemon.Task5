package by.lomazki.pokemontask5.presentation.di

import by.lomazki.pokemontask5.presentation.ui.DetailsViewModel
import by.lomazki.pokemontask5.presentation.ui.FavoriteViewModel
import by.lomazki.pokemontask5.presentation.ui.MapViewModel
import by.lomazki.pokemontask5.presentation.ui.PokemonViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {

    viewModelOf(::PokemonViewModel)
    viewModelOf(::DetailsViewModel)
    viewModelOf(::FavoriteViewModel)
    viewModelOf(::MapViewModel)
}
