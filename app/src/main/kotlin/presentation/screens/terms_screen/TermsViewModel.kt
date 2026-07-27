package presentation.screens.terms_screen

import androidx.lifecycle.ViewModel
import com.dunihuliapps.myglidingassistant.data.repositories.onboarding.OnboardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TermsViewModel @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) : ViewModel() {

    private val _isChecked = MutableStateFlow(false)
    val isChecked: StateFlow<Boolean> = _isChecked.asStateFlow()

    fun onCheckedChange(checked: Boolean) {
        _isChecked.value = checked
    }

    fun acceptTerms() {
        onboardingRepository.setTermsAccepted()
    }
}
