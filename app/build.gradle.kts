import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.oss.licenses)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    kotlin("kapt") // Keep kapt for Hilt for now
}

android {
    namespace = "com.dunihuliapps.myglidingassistant" // Adjust to your package
    compileSdk = 35

    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("app_local.properties")
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use { localProperties.load(it) }
    }

    defaultConfig {
        applicationId = "com.dunihuliapps.myglidingassistant"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        val googleMapsApiKey = localProperties.getProperty("GOOGLE_MAPS_API_KEY")
        manifestPlaceholders["googleMapsApiKey"] = googleMapsApiKey
        // The Places SDK needs the key at runtime (Places.initialize), unlike the Maps SDK
        // which only reads it from the manifest meta-data.
        buildConfigField("String", "GOOGLE_MAPS_API_KEY", "\"$googleMapsApiKey\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
        compose = true
        buildConfig = true
    }
}

// firebase-analytics's latest play-services-measurement releases are compiled with Kotlin 2.2
// metadata, which this project's Kotlin 2.0.21 toolchain can't read. Pin the last measurement
// release built with compatible metadata until the project does a full Kotlin/Hilt/AGP bump.
configurations.all {
    resolutionStrategy {
        force(
            "com.google.android.gms:play-services-measurement:22.4.0",
            "com.google.android.gms:play-services-measurement-api:22.4.0",
            "com.google.android.gms:play-services-measurement-base:22.4.0",
            "com.google.android.gms:play-services-measurement-impl:22.4.0",
            "com.google.android.gms:play-services-measurement-sdk:22.4.0",
            "com.google.android.gms:play-services-measurement-sdk-api:22.4.0",
        )
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.preference.ktx)
    implementation(libs.androidx.legacy.support)

    implementation(libs.picasso)

    // Core Compose libraries
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // Integration with Activity & ViewModels
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.material.icons.core)
// In dependencies block
    // In dependencies block
    implementation("androidx.compose.material:material-icons-extended:1.7.8")
// For loading the selected image from a URI:
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation(libs.androidx.ui)
    implementation(libs.androidx.compose.foundation.layout)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)


    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Maps & Serialization
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
    implementation("com.google.android.gms:play-services-oss-licenses:17.0.1")

    // Airfield location search + picker map
    implementation(libs.places)
    implementation(libs.maps.compose)

    implementation (files("libs/jdom-2.0.6.jar"))
    implementation (files("libs/jdom-2.0.6-javadoc.jar"))

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation("com.google.firebase:firebase-analytics:22.4.0")

}