package presentation.screens.airfields.edit_airfield_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dunihuliapps.myglidingassistant.data.repositories.airfields.AirfieldsRepository
import com.dunihuliapps.myglidingassistant.domain.places.PlacePrediction
import com.dunihuliapps.myglidingassistant.domain.places.PlacesSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class EditAirfieldViewModel @Inject constructor(
    private val repository: AirfieldsRepository,
    private val placesSearchRepository: PlacesSearchRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(
        EditAirfieldScreenState(
            name = savedStateHandle.get<String>("name"),
            latitude = savedStateHandle.get<String>("latitude")?.toDoubleOrNull(),
            longitude = savedStateHandle.get<String>("longitude")?.toDoubleOrNull(),
            isHome = savedStateHandle.get<Boolean>("isHome") ?: false,
        )
    )
    val state = _state.asStateFlow()
    val isEditMode: Boolean get() = (savedStateHandle.get<Long>("id") ?: 0L) > 0L

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _predictions = MutableStateFlow<List<PlacePrediction>>(emptyList())
    val predictions = _predictions.asStateFlow()

    init {
        viewModelScope.launch {
            _searchQuery.debounce(SEARCH_DEBOUNCE_MS).collect { query ->
                _predictions.value = if (query.length >= MIN_QUERY_LENGTH) {
                    placesSearchRepository.searchPredictions(query)
                } else {
                    emptyList()
                }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onPredictionSelected(prediction: PlacePrediction) {
        viewModelScope.launch {
            placesSearchRepository.fetchPlaceLocation(prediction.placeId)?.let { location ->
                _state.value = _state.value.copy(
                    name = _state.value.name?.takeIf { it.isNotBlank() } ?: location.name,
                    latitude = location.latitude,
                    longitude = location.longitude,
                )
            }
            _searchQuery.value = ""
            _predictions.value = emptyList()
        }
    }

    fun mapEventToState(event: EditAirfieldEvent) {
        when (event) {
            is EditAirfieldEvent.OnNameChange -> _state.value = _state.value.copy(name = event.name)
            is EditAirfieldEvent.OnLocationSelected -> _state.value = _state.value.copy(
                latitude = event.latitude,
                longitude = event.longitude,
            )
            is EditAirfieldEvent.OnHomeToggled -> _state.value = _state.value.copy(isHome = event.isHome)
            is EditAirfieldEvent.Save -> save {}
        }
    }

    fun save(onComplete: () -> Unit = {}) {
        val latitude = _state.value.latitude
        val longitude = _state.value.longitude
        if (latitude == null || longitude == null) return

        viewModelScope.launch {
            val airfieldId = savedStateHandle.get<Long>("id") ?: 0L
            val name = _state.value.name?.takeIf { it.isNotBlank() } ?: ""
            if (airfieldId > 0L) {
                repository.update(
                    airfieldId,
                    name = name,
                    latitude = latitude,
                    longitude = longitude,
                    isHome = _state.value.isHome
                )
            } else {
                repository.insertAirfield(
                    name = name,
                    latitude = latitude,
                    longitude = longitude,
                    isHome = _state.value.isHome
                )
            }
            onComplete()
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_MS = 300L
        private const val MIN_QUERY_LENGTH = 2
    }
}
