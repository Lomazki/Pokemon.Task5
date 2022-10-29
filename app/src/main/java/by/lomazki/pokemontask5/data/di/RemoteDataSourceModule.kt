package by.lomazki.pokemontask5.data.di

import by.lomazki.pokemontask5.data.datasource.RemoteDataSourceImpl
import by.lomazki.pokemontask5.domain.datasource.RemoteDataSource
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val remoteDsModule = module {
    singleOf(::RemoteDataSourceImpl) {
        bind<RemoteDataSource>()
    }
}
