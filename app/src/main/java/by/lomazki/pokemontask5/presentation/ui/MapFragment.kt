package by.lomazki.pokemontask5.presentation.ui

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.lomazki.pokemontask5.databinding.FragmentMapBinding
import by.lomazki.pokemontask5.extentions.hasPermission
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.LocationSource
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = requireNotNull(_binding)

    private var googleMap: GoogleMap? = null
    private var locationListener: LocationSource.OnLocationChangedListener? = null

    private val viewModel by viewModel<MapViewModel>()

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isEnabled ->
        setLocationEnabled(isEnabled)
        if (isEnabled) {
            observeLocationChanges()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMapBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        binding.mapContainer.onCreate(savedInstanceState)
        initGoogleMap()
    }

    @SuppressLint("MissingPermission")
    private fun setLocationEnabled(enabled: Boolean) {
        googleMap?.isMyLocationEnabled = enabled
        googleMap?.uiSettings?.isMyLocationButtonEnabled = enabled
    }

    private fun initGoogleMap() = with(binding) {
        mapContainer.getMapAsync { map ->
            googleMap = map.apply {

                uiSettings.isCompassEnabled = true
                uiSettings.isZoomControlsEnabled = true
                uiSettings.isMyLocationButtonEnabled = true

                setLocationSource(object : LocationSource {
                    override fun activate(listener: LocationSource.OnLocationChangedListener) {
                        locationListener = listener
                    }

                    override fun deactivate() {
                        locationListener = null
                    }
                })
            }

            val hasLocationPermission =
                requireContext().hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            setLocationEnabled(hasLocationPermission)

            if (requireContext().hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
                observeLocationChanges()
            }

            val minsk = LatLng(53.942209922672106, 27.62675542875292)
            map.addMarker(
                MarkerOptions()
                    .position(minsk)
                    .title("Minsk")
            )
            map.moveCamera(CameraUpdateFactory.newLatLng(minsk))
        }
    }

    private fun observeLocationChanges() {
        viewModel
            .startLocationFlow
            .onEach(::moveCameraToLocation)
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel
            .locationFlow
            .onEach {
                locationListener?.onLocationChanged(it)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapContainer.onSaveInstanceState(outState)
    }

    private fun moveCameraToLocation(location: Location) {
        val current = LatLng(location.latitude, location.latitude)
        googleMap?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(current, DEFAULT_CAMERA_ZOOM)
        )
    }

    override fun onResume() {
        super.onResume()
        binding.mapContainer.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapContainer.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        binding.mapContainer.onDestroy()
        googleMap = null
    }

    companion object {
        private const val DEFAULT_CAMERA_ZOOM = 2f
    }
}
