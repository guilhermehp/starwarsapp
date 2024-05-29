package com.example.starwarsapp.helpers

import com.example.starwarsapp.data.di.NetworkModule
import com.example.starwarsapp.mocks.FakeApiService
import com.example.starwarsapp.data.retrofit.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
class TestNetworkModule {

    @Provides
    @Singleton
    fun provideFakeApiService(): ApiService {
        return FakeApiService()
    }

}