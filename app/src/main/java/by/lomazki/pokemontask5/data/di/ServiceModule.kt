package by.lomazki.pokemontask5.data.di

import by.lomazki.pokemontask5.data.services.LocationService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val serviceModule = module {
    singleOf(::LocationService)
}
