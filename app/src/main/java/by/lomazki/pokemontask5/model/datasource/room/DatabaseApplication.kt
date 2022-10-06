package by.lomazki.pokemontask5.model.datasource.room

import android.app.Application
import androidx.room.Room
import by.lomazki.pokemontask5.model.ServiceLocator.init

class DatabaseApplication : Application() {

    private var _database: PokemonDatabase? = null
    val database get() = requireNotNull(_database)

    override fun onCreate() {
        super.onCreate()

        init(this)
        _database = Room
            .databaseBuilder(
                this,
                PokemonDatabase::class.java,
                "database"
            ).allowMainThreadQueries().build()
    }
}