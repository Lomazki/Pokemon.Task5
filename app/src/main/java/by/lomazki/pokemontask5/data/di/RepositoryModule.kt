package by.lomazki.pokemontask5.data.di

import by.lomazki.pokemontask5.data.repository.RepositoryImpl
import by.lomazki.pokemontask5.domain.repository.Repository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {

    singleOf(::RepositoryImpl) {
        bind<Repository>()
    }
}
