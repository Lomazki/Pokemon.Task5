package by.lomazki.pokemontask5.presentation.ui.map

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.lomazki.pokemontask5.data.services.LocationService
import kotlinx.coroutines.flow.*

class MapViewModel(
    private val locationService: LocationService
) : ViewModel() {

    val locationFlow: Flow<Location> by locationService::locationFlow

    val startLocationFlow = flow {
        locationService.getLocation()?.let { emit(it) }
    }.shareIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        replay = 0
    )
}
