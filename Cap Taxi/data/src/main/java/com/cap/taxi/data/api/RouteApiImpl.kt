package com.cap.taxi.data.api

import com.cap.taxi.data.BuildConfig
import com.google.maps.model.LatLng
import com.google.maps.DirectionsApiRequest
import com.google.maps.GeoApiContext
import com.google.maps.internal.PolylineEncoding
import com.google.maps.model.DirectionsResult
import com.google.maps.model.TravelMode
import com.cap.taxi.domain.data.IRouteApi
import com.cap.taxi.domain.model.taxi.MapLocationInfo
import com.cap.taxi.domain.model.taxi.RouteInfo
import com.google.maps.PendingResult.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class RouteApiImpl : IRouteApi {

    override suspend fun makeRoute(
        from: MapLocationInfo,
        to: MapLocationInfo
    ): RouteInfo = withContext(Dispatchers.IO) {

        val ctx = GeoApiContext.Builder()
            .apiKey(BuildConfig.GOOGLE_MAP_KEY)
            .build()

        try {
            val result: DirectionsResult = withTimeout(60 * 1000L) {
                suspendCancellableCoroutine { cont ->
                    DirectionsApiRequest(ctx)
                        .alternatives(false)
                        .mode(TravelMode.DRIVING)
                        .origin(LatLng(from.latitude, from.longitude))
                        .destination(LatLng(to.latitude, to.longitude))
                        .setCallback(object : Callback<DirectionsResult> {
                            override fun onResult(r: DirectionsResult) {
                                if (cont.isActive) cont.resume(r)
                            }
                            override fun onFailure(e: Throwable) {
                                if (cont.isActive) cont.resumeWithException(e)
                            }
                        })
                }
            }

            val routeDuration = result.routes[0].legs[0].duration.inSeconds
            val routeDistance = result.routes[0].legs[0].distance.inMeters
            val routeCurve = getPolyline(result)

            RouteInfo(
                routeDuration,
                routeDistance,
                routeCurve
            )
        } finally {
            ctx.shutdown()
        }
    }

    private fun getPolyline(result: DirectionsResult): List<MapLocationInfo> {
        for (route in result.routes) {
            val decodedPath = PolylineEncoding.decode(route.overviewPolyline.encodedPath)

            val newDecodedPath: ArrayList<MapLocationInfo> = ArrayList()

            // This loops through all the LatLng coordinates of ONE polyline.
            for (latLng in decodedPath) {
                newDecodedPath.add(
                    MapLocationInfo(
                        latLng.lat,
                        latLng.lng
                    )
                )
            }

            return newDecodedPath
        }
        return listOf()
    }
}