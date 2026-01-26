package com.cap.taxi.data.api

import com.cap.taxi.data.BuildConfig
import com.cap.taxi.domain.data.IPlacesApi
import com.cap.taxi.domain.model.taxi.MapLocationInfo
import com.cap.taxi.domain.model.taxi.MapPlaceInfo
import com.cap.taxi.domain.usecase.other.IsNetworkConnectionUc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale


class PlacesApiImpl (
    private val googlePlacesService: GooglePlacesService,
    private val isNetworkConnectionUc: IsNetworkConnectionUc
) : IPlacesApi {

    companion object {
        private const val TAG = "GooglePlacesApi"
    }

    // Requires internet connection
    override suspend fun makePlaceSearchRequest(text: String): List<MapPlaceInfo> =
        withContext(Dispatchers.IO) {
            if (!isNetworkConnectionUc.execute()) {
                throw RuntimeException("$TAG.makeSearchRequest: No internet connection error")
            }

            val predictions = googlePlacesService.getPredictions(
                text,
                getSupportedCodeOfCountry(),
                BuildConfig.GOOGLE_MAP_KEY
            ).body()?.predictions ?: listOf()


            val places = ArrayList<MapPlaceInfo>(predictions.size)

            predictions.forEach { prediction ->
                places.add(
                    MapPlaceInfo(
                        prediction.description,
                        prediction.place_id
                    )
                )
            }

            return@withContext places
        }

    // Requires internet connection
    override suspend fun makePlaceSearchRequest(
        text: String,
        location: MapLocationInfo
    ): List<MapPlaceInfo> = withContext(Dispatchers.IO) {
        if (!isNetworkConnectionUc.execute()) {
            throw RuntimeException("$TAG.makeSearchRequest: No internet connection error")
        }

        val predictions = googlePlacesService.getPredictions(
            text,
            "circle:10000@${location.latitude},${location.longitude}",
            getSupportedCodeOfCountry(),
            BuildConfig.GOOGLE_MAP_KEY
        ).body()?.predictions ?: listOf()

        val places = ArrayList<MapPlaceInfo>(predictions.size)

        predictions.forEach { prediction ->
            places.add(
                MapPlaceInfo(
                    prediction.description,
                    prediction.place_id
                )
            )
        }

        return@withContext places
    }

    // Requires internet connection
    override suspend fun makePlaceInfoRequest(placeId: String): MapLocationInfo =
        withContext(Dispatchers.IO) {
            if (!isNetworkConnectionUc.execute()) {
                throw RuntimeException("$TAG.makePlaceRequest: No internet connection error")
            }

            val placeInfo = googlePlacesService.getPlaceInfo(
                placeId,
                "address_components,geometry",
                BuildConfig.GOOGLE_MAP_KEY
            ).body()!!

            val location = MapLocationInfo(
                placeInfo.result.geometry.location.lat,
                placeInfo.result.geometry.location.lng
            )

            return@withContext location
        }


    private val supporterCodes = listOf("af", "ja", "sq", "kn", "am", "kk", "ar", "km", "hy", "ko","az", "ky", "eu", "lo", "be", "lv", "bn", "lt", "bs", "mk","bg", "ms", "my", "ml", "ca", "mr", "zh", "mn", "zh-CN", "ne","zh-HK", "no", "zh-TW", "pl", "hr", "pt", "cs", "pt-BR", "da", "pt-PT","nl", "pa", "en", "ro", "en-AU", "ru", "en-GB", "sr", "et", "si","fa", "sk", "fi", "sl", "fil", "es", "fr", "es-419", "fr-CA", "sw","gl", "sv", "ka", "ta", "de", "te", "el", "th", "gu", "tr","iw", "uk", "hi", "ur", "hu", "uz", "is", "vi", "id", "zu")
    private fun getSupportedCodeOfCountry(): String{
        val locale = Locale.getDefault().language

        if (supporterCodes.contains(locale)){
            return locale
        }
        return "en"
    }
}

