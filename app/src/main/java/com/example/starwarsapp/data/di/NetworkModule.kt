package com.example.starwarsapp.data.di

import com.example.starwarsapp.data.retrofit.ApiService
import com.example.starwarsapp.data.retrofit.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService{
        return RetrofitClient.apiService
    }


}