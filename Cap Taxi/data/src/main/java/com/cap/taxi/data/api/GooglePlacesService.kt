package com.cap.taxi.data.api


import androidx.annotation.Keep
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

@Keep
interface GooglePlacesService {

    @GET("maps/api/place/autocomplete/json")
    suspend fun getPredictions(
        @Query("input") text: String,
        @Query("locationbias") location: String,
        @Query("language") language: String,
        @Query("key") apiKey: String,
    ): Response<Predictions>

    @GET("maps/api/place/autocomplete/json")
    suspend fun getPredictions(
        @Query("input") text: String,
        @Query("language") language: String,
        @Query("key") apiKey: String,
    ): Response<Predictions>

    @GET("maps/api/place/details/json")
    suspend fun getPlaceInfo(
        @Query("place_id") place: String,
        @Query("fields") fields: String,
        @Query("key") apiKey: String,
    ): Response<PlaceInfo>

    companion object{
        const val BASE_URL = "https://maps.googleapis.com/"
    }
}

@Keep
data class Location(
    val lat: Double,
    val lng: Double
)

@Keep
data class Geometry(
    val location: Location,
)

@Keep
data class AddressComponents(
    val long_name: String,
    val short_name: String,
    val types: List<String>
)

@Keep
data class Result(
    val geometry: Geometry,
)

@Keep
data class PlaceInfo(
    val address_components: List<AddressComponents>,
    val result: Result,
    val status: String
)

@Keep
data class Predictions(
    val predictions: List<Prediction>
)

@Keep
data class Prediction(
    val description: String,
    val matched_substrings: List<MatchedSubstring>,
    val place_id: String,
    val reference: String,
    val structured_formatting: StructuredFormatting,
    val terms: List<Term>,
    val types: List<String>
)

@Keep
data class MatchedSubstring(
    val length: Int,
    val offset: Int
)

@Keep
data class StructuredFormatting(
    val main_text: String,
    val main_text_matched_substrings: List<MatchedSubstring>,
)

@Keep
data class Term(
    val offset: Int,
    val value: String
)