package com.example.starwarsapp.data.di

import com.example.starwarsapp.domain.utils.AnimationConfig
import com.example.starwarsapp.domain.utils.AnimationConfigInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AnimationConfigModule {

    @Provides
    @Singleton
    fun provideAnimationConfiguration(): AnimationConfigInterface {
        return AnimationConfig
    }
}