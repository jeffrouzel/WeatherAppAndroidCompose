package com.example.weatherappandroidcompose.di

import android.content.Context
import com.example.weatherappandroidcompose.repository.CityDataStore
import com.example.weatherappandroidcompose.repository.WeatherRepository
import com.example.weatherappandroidcompose.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(): WeatherRepository {
        return WeatherRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideCityDataStore(
        @ApplicationContext context: Context
    ): CityDataStore {
        return CityDataStore(context)
    }
}