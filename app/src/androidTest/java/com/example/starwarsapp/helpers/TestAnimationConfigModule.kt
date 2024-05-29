package com.example.starwarsapp.helpers

import com.example.starwarsapp.data.di.AnimationConfigModule
import com.example.starwarsapp.domain.utils.AnimationConfigInterface
import com.example.starwarsapp.mocks.FakeAnimationConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AnimationConfigModule::class]
)
class TestAnimationConfigModule {

    @Provides
    @Singleton
    fun provideAnimationConfiguration(): AnimationConfigInterface {
        return FakeAnimationConfig
    }

}