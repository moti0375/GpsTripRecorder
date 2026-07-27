package com.dunihuliapps.myglidingassistant.domain.places

import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

data class PlacePrediction(
    val placeId: String,
    val primaryText: String,
    val secondaryText: String,
)

data class PlaceLocation(
    val name: String,
    val latitude: Double,
    val longitude: Double,
)

interface PlacesSearchRepository {
    suspend fun searchPredictions(query: String): List<PlacePrediction>
    suspend fun fetchPlaceLocation(placeId: String): PlaceLocation?
}

class PlacesSearchRepositoryImpl @Inject constructor(
    private val placesClient: PlacesClient
) : PlacesSearchRepository {

    private var sessionToken = AutocompleteSessionToken.newInstance()

    override suspend fun searchPredictions(query: String): List<PlacePrediction> {
        if (query.isBlank()) return emptyList()
        val request = FindAutocompletePredictionsRequest.builder()
            .setSessionToken(sessionToken)
            .setQuery(query)
            .build()

        return suspendCancellableCoroutine { continuation ->
            placesClient.findAutocompletePredictions(request)
                .addOnSuccessListener { response ->
                    val predictions = response.autocompletePredictions.map {
                        PlacePrediction(
                            placeId = it.placeId,
                            primaryText = it.getPrimaryText(null).toString(),
                            secondaryText = it.getSecondaryText(null).toString(),
                        )
                    }
                    continuation.resume(predictions)
                }
                .addOnFailureListener { continuation.resume(emptyList()) }
        }
    }

    override suspend fun fetchPlaceLocation(placeId: String): PlaceLocation? {
        val request = FetchPlaceRequest.newInstance(placeId, PLACE_FIELDS)
        return suspendCancellableCoroutine { continuation ->
            placesClient.fetchPlace(request)
                .addOnSuccessListener { response ->
                    val latLng = response.place.latLng
                    if (latLng != null) {
                        continuation.resume(
                            PlaceLocation(
                                name = response.place.name ?: "",
                                latitude = latLng.latitude,
                                longitude = latLng.longitude,
                            )
                        )
                        // Start a fresh session now that this search has completed —
                        // autocomplete billing is per-session, ended by a fetchPlace call.
                        sessionToken = AutocompleteSessionToken.newInstance()
                    } else {
                        continuation.resume(null)
                    }
                }
                .addOnFailureListener { continuation.resume(null) }
        }
    }

    companion object {
        private val PLACE_FIELDS = listOf(Place.Field.LAT_LNG, Place.Field.NAME)
    }
}
