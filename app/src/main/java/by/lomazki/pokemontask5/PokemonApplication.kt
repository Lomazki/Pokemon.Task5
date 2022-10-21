package by.lomazki.pokemontask5

import android.app.Application
import by.lomazki.pokemontask5.data.di.*
import by.lomazki.pokemontask5.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PokemonApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PokemonApplication)
            modules(
//                dataModule,
                viewModelModule,
                apiModule,
                repositoryModule,
                roomModule,
                localDsModule,
                remoteDsModule
            )
        }
    }
}