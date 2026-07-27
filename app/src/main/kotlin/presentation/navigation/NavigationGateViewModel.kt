package presentation.navigation

import androidx.lifecycle.ViewModel
import com.dunihuliapps.myglidingassistant.data.repositories.onboarding.OnboardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavigationGateViewModel @Inject constructor(
    onboardingRepository: OnboardingRepository
) : ViewModel() {

    val startDestination: String = when {
        !onboardingRepository.hasCompletedOnboarding() -> "onboarding"
        !onboardingRepository.hasAcceptedTerms() -> "terms"
        else -> "permissions"
    }
}
