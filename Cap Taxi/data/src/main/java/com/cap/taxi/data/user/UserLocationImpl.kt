package com.cap.taxi.data.user

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.cap.taxi.domain.data.IUserLocation
import com.cap.taxi.domain.model.taxi.MapLocationInfo
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserLocationImpl(
    private val context: Context
): IUserLocation {

    lateinit var fusedLocationClient: FusedLocationProviderClient

    override suspend fun getUserLocation(): MapLocationInfo? =
        withContext(Dispatchers.IO) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                ||
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

                val current = fusedLocationClient.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    null
                ).await()


                if (current != null) {
                    return@withContext MapLocationInfo(
                        current.latitude,
                        current.longitude
                    )
                }
            }

            return@withContext null
        }
}