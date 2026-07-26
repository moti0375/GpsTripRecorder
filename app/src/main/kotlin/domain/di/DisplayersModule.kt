package com.dunihuliapps.myglidingassistant.domain.di
import com.dunihuliapps.myglidingassistant.domain.formatters.TimeFormatter
import com.dunihuliapps.myglidingassistant.presentation.units_formatters.HmsFormatter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DisplayersModule {
    @Binds
    abstract fun bindsTimeDisplayer(timeDisplayer: HmsFormatter) : TimeFormatter
}