package com.dunihuliapps.myglidingassistant.data.repositories.onboarding

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

interface OnboardingRepository {
    fun hasCompletedOnboarding(): Boolean
    fun setOnboardingCompleted()
    fun hasAcceptedTerms(): Boolean
    fun setTermsAccepted()
}

class OnboardingRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : OnboardingRepository {

    override fun hasCompletedOnboarding(): Boolean =
        sharedPreferences.getBoolean(KEY_ONBOARDING_COMPLETED, false)

    override fun setOnboardingCompleted() {
        sharedPreferences.edit { putBoolean(KEY_ONBOARDING_COMPLETED, true) }
    }

    override fun hasAcceptedTerms(): Boolean =
        sharedPreferences.getBoolean(KEY_TERMS_ACCEPTED, false)

    override fun setTermsAccepted() {
        sharedPreferences.edit { putBoolean(KEY_TERMS_ACCEPTED, true) }
    }

    companion object {
        private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
        private const val KEY_TERMS_ACCEPTED = "terms_accepted"
    }
}
