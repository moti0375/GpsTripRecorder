package com.dunihuliapps.myglidingassistant.domain.di

import android.content.Context
import com.dunihuliapps.myglidingassistant.domain.places.PlacesSearchRepository
import com.dunihuliapps.myglidingassistant.domain.places.PlacesSearchRepositoryImpl
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PlacesModule {

    @Binds
    @Singleton
    abstract fun bindPlacesSearchRepository(impl: PlacesSearchRepositoryImpl): PlacesSearchRepository

    companion object {
        @Provides
        @Singleton
        fun providePlacesClient(@ApplicationContext context: Context): PlacesClient {
            return Places.createClient(context)
        }
    }
}
