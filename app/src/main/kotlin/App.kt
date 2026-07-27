package com.dunihuliapps.myglidingassistant
import android.app.Application
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.MapsInitializer.Renderer
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // Maps SDK 18+ requires explicit initialization before any SupportMapFragment
        // is created. Without this, onCreateView fails with a Play Services error
        // when the fragment is added synchronously early in the activity lifecycle.
        MapsInitializer.initialize(this, Renderer.LATEST, null)

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, BuildConfig.GOOGLE_MAPS_API_KEY)
        }
    }
}