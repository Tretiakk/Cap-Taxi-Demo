package com.cap.taxi.viewmodel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cap.taxi.R
import com.cap.taxi.domain.model.taxi.MapPlaceInfo
import com.cap.taxi.domain.model.taxi.MapLocationInfo
import com.cap.taxi.facade.OverlayController
import com.cap.taxi.facade.TaxiModel
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ViewModelTaxi @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val model: TaxiModel,
    private val overlay: OverlayController
) : ViewModel() {


    companion object {
        private var isFineLocationPermissionGranted = false
        private var isCoarseLocationPermissionGranted = false

        private var isDeparturePointField = true
    }


    private val _priceOfTrip = MutableStateFlow<Int?>(null)
    private val _currentRoadPath = MutableStateFlow<List<LatLng>>(listOf())
    private val _searchPlacesList = MutableStateFlow<List<MapPlaceInfo>>(listOf())

    private val _userLocation: MutableStateFlow<MapLocationInfo?> = MutableStateFlow(null)
    private val _placeOfDeparture = MutableStateFlow<MapLocationInfo?>(null)
    private val _placeOfArrival = MutableStateFlow<MapLocationInfo?>(null)
    private val _textOfDeparturePoint = MutableStateFlow("")
    private val _textOfArrivalPoint = MutableStateFlow("")


    /////////////////
    /////////////////

    val priceOfTrip = _priceOfTrip.asStateFlow()
    val currentRoadPath = _currentRoadPath.asStateFlow()
    val searchPlacesList = _searchPlacesList.asStateFlow()

    val userLocation = _userLocation.asStateFlow()
    val placeOfDeparture = _placeOfDeparture.asStateFlow()
    val placeOfArrival = _placeOfArrival.asStateFlow()
    val textOfDeparturePoint = _textOfDeparturePoint.asStateFlow()
    val textOfArrivalPoint = _textOfArrivalPoint.asStateFlow()

    private var locationPermissionRequest: ActivityResultLauncher<Array<String>>? = null

    fun realize(activity: ComponentActivity) {
        subscribe()

        locationPermissionRequest = activity.registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                when {
                    permissions.getOrDefault(
                        Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                        // Precise location access granted.
                        isFineLocationPermissionGranted = true

                        requestUserLocation()
                    }

                    permissions.getOrDefault(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        false) -> {
                        // Approximate location access granted.
                        isCoarseLocationPermissionGranted = true

                        requestUserLocation()
                    }

                    else -> {
                        // No location access granted.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                activity, Manifest.permission.ACCESS_COARSE_LOCATION)) {

                            overlay.message(
                                shortDescription = context.getString(R.string.problem),
                                description = context.getString(R.string.we_need_location_permission_description),
                                buttonText = context.getString(R.string.confirm),
                                onButtonClick = { hide ->
                                    requestLocationPermissions()

                                    hide()
                                }
                            )
                        }
                    }
                }
            }

        // network connection check
        overlay.isNetworkConnected()
    }

    fun start() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermissions()
        } else {
            requestUserLocation()
        }
    }

    private fun subscribe() {
        viewModelScope.launch {
            model.currentRoute.collect { currentRoute ->
                if (currentRoute != null) {
                    _currentRoadPath.value = model.convertToLatLng(currentRoute.routeCurve)

                    setPrice(model.getCurrentRoutePrice())
                } else {
                    // Failed to put road
                    _currentRoadPath.value = listOf()
                    setPrice(null)
                }
            }
        }

        viewModelScope.launch {
            placeOfDeparture.collect {
                clearRoad()
            }
        }

        viewModelScope.launch {
            placeOfArrival.collect {
                clearRoad()
            }
        }
    }

    fun selectPlace(place: MapPlaceInfo, onPlaceFound: (placeInfo: MapLocationInfo) -> Unit) {
        clearSearchPlacesList()

        // Network check
        if (!overlay.isNetworkConnected()) {
            overlay.message(
                context.getString(R.string.problem),
                context.getString(R.string.internet_error),
                context.getString(R.string.ok)
            )

            return
        }

        viewModelScope.launch {
            val placeResponse = model.makePlaceInfoRequest(place.placeId)

            if (placeResponse == null) {
                throw RuntimeException("Retrieving place info error: response == null")
            }

            if (isDeparturePointField) {
                _placeOfDeparture.value = placeResponse
                _textOfDeparturePoint.value = place.description
            } else {
                _placeOfArrival.value = placeResponse
                _textOfArrivalPoint.value = place.description
            }

            withContext(Dispatchers.Main) {
                onPlaceFound(
                    MapLocationInfo(
                        placeResponse.latitude,
                        placeResponse.longitude
                    )
                )
            }
        }

    }

    fun putRoad() {
        // Network check
        if (!overlay.isNetworkConnected()) {
            overlay.message(
                context.getString(R.string.problem),
                context.getString(R.string.internet_error),
                context.getString(R.string.ok)
            )

            return
        }

        if (placeOfDeparture.value != null && placeOfArrival.value != null) {
            val from = placeOfDeparture.value!!
            val to = placeOfArrival.value!!

            viewModelScope.launch {
                model.putRoad(from, to)
            }
        }

        else {
            // massage about empty of one field
            if (placeOfDeparture.value == null) {
                overlay.message(
                    shortDescription = context.getString(R.string.problem),
                    buttonText = context.getString(R.string.ok),
                    description = context.getString(R.string.enter_departure_point)
                )
            }

            else if (placeOfArrival.value == null) {
                overlay.message(
                    shortDescription = context.getString(R.string.problem),
                    buttonText = context.getString(R.string.ok),
                    description = context.getString(R.string.enter_point_of_arrival),
                )
            } else {
                overlay.message(
                    shortDescription = context.getString(R.string.problem),
                    buttonText = context.getString(R.string.ok),
                    description = context.getString(R.string.error_occurred),
                )
            }
        }
    }

    fun searchPlacesDeparture(text: String) {
        _textOfDeparturePoint.value = text

        isDeparturePointField = true

        requestSearchPlacesBar(text)
    }

    fun searchPlacesArrival(text: String) {
        _textOfArrivalPoint.value = text

        isDeparturePointField = false

        requestSearchPlacesBar(text)
    }

    fun selectUserLocationAsDeparture() {
        viewModelScope.launch {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                ||
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
            ) {
                val location = model.getUserLocation()

                _userLocation.value = location

                _placeOfDeparture.value = userLocation.value
                _textOfDeparturePoint.value = context.getString(R.string.your_location)
            } else {
                overlay.message(
                    shortDescription = context.getString(R.string.problem),
                    description = context.getString(R.string.we_cannot_get_your_location),
                    buttonText = context.getString(R.string.ok)
                )
            }
        }
    }

    private fun requestUserLocation() {
        viewModelScope.launch {
            _userLocation.value = model.getUserLocation()
        }
    }

    private fun clearSearchPlacesList() {
        _searchPlacesList.value = listOf()
    }

    private fun clearRoad() {
        _currentRoadPath.value = listOf()
    }

    private fun setPrice(price: Int?) {
        _priceOfTrip.value = price
    }

    private fun requestSearchPlacesBar(text: String) {
        // Network check
        if (!overlay.isNetworkConnected()) {
            overlay.message(
                context.getString(R.string.problem),
                context.getString(R.string.internet_error),
                context.getString(R.string.ok)
            )

            return
        }

        viewModelScope.launch {
            if (userLocation.value != null){
                _searchPlacesList.value = model.makePlaceSearchRequest(text, userLocation.value)
            } else {
                _searchPlacesList.value = model.makePlaceSearchRequest(text, null)
            }
        }
    }

    private fun requestLocationPermissions(){
        if (locationPermissionRequest != null) {
            locationPermissionRequest!!.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        } else {
            throw RuntimeException("Location permission request wasn't initialized")
        }
    }
}