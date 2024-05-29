package com.example.starwarsapp.helpers

import com.example.starwarsapp.data.di.DatabaseModule
import com.example.starwarsapp.data.room.FavoriteDao
import com.example.starwarsapp.mocks.FakeFavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
class TestDatabaseModule {

    @Provides
    @Singleton
    fun provideFakeFavoriteDao(): FavoriteDao {
        return FakeFavoriteDao()
    }

}