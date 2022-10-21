package by.lomazki.pokemontask5.data.di

import by.lomazki.pokemontask5.data.datasource.LocalDataSourceImpl
import by.lomazki.pokemontask5.domain.datasource.LocalDataSource
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val localDsModule = module {
        singleOf(::LocalDataSourceImpl){
            bind<LocalDataSource>()
        }
}