package com.dunihuliapps.myglidingassistant.domain.files.path_provider
import com.dunihuliapps.myglidingassistant.domain.di.QDefaultFilesDir
import com.dunihuliapps.myglidingassistant.domain.di.QShareImagesDir
import com.dunihuliapps.myglidingassistant.domain.di.QTripsImagesDir
import com.dunihuliapps.myglidingassistant.domain.di.QTripsKmlDir
import java.io.File
import javax.inject.Inject

interface PathProvider {
    fun provideRootFilesPath() : File
    fun providerImagesFilesPath() : File
    fun provideTripKmlFilesPath() : File
    fun providesShareImagesDir() : File
}

class PathProviderImpl @Inject constructor(
    @QDefaultFilesDir private val defaultFilesDir: String,
    @QTripsImagesDir private val tripImagesDir: String,
    @QTripsKmlDir private val tripsKmlDir: String,
    @QShareImagesDir private val shareImagesDir: String
) : PathProvider {
    override fun provideRootFilesPath(): File {
        return File(defaultFilesDir)
    }

    override fun providerImagesFilesPath(): File {
        return File(tripImagesDir)
    }

    override fun provideTripKmlFilesPath(): File {
        return File(tripsKmlDir)
    }

    override fun providesShareImagesDir(): File {
        return File(shareImagesDir)
    }
}