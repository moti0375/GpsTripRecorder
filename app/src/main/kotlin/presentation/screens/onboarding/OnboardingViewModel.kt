package presentation.screens.onboarding

import androidx.lifecycle.ViewModel
import com.dunihuliapps.myglidingassistant.data.repositories.onboarding.OnboardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) : ViewModel() {

    val hasAcceptedTerms: Boolean
        get() = onboardingRepository.hasAcceptedTerms()

    fun completeOnboarding() {
        onboardingRepository.setOnboardingCompleted()
    }
}
