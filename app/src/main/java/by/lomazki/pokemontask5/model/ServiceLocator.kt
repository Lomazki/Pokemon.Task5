package by.lomazki.pokemontask5.model

import android.content.Context
import by.lomazki.pokemontask5.extentions.appDatabase
import by.lomazki.pokemontask5.model.datasource.api.ApiService

object ServiceLocator {

    private lateinit var contextApp: Context

    private val repository by lazy {
        Repository(
            database = contextApp.appDatabase,
            apiService = ApiService
        )
    }

    fun init(context: Context) {
        this.contextApp = context
    }

    fun provideDataSource(): Repository = repository
}
