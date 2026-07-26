package presentation.screens.gliders.gliders_screen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dunihuliapps.myglidingassistant.data.model.Glider
import com.dunihuliapps.myglidingassistant.data.repositories.gliders.GlidersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.emptyList

@HiltViewModel
class GlidersViewModel @Inject constructor(
    private val repository: GlidersRepository
) : ViewModel() {
    val gliders = repository.getAllGliders().stateIn(viewModelScope,
        SharingStarted.WhileSubscribed(5000), emptyList())

    fun deleteGlider(gliderId: Glider) {
        viewModelScope.launch {
            repository.deleteGlider(gliderId)
        }
    }

}