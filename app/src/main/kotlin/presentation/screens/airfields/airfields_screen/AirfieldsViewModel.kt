package presentation.screens.airfields.airfields_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dunihuliapps.myglidingassistant.data.model.Airfield
import com.dunihuliapps.myglidingassistant.data.repositories.airfields.AirfieldsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AirfieldsViewModel @Inject constructor(
    private val repository: AirfieldsRepository
) : ViewModel() {
    val airfields = repository.getAllAirfields().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    fun deleteAirfield(airfield: Airfield) {
        viewModelScope.launch {
            repository.deleteAirfield(airfield)
        }
    }
}
