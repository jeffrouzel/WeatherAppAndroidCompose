package com.example.weatherappandroidcompose.di

import com.example.weatherappandroidcompose.repository.WeatherRepository
import com.example.weatherappandroidcompose.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
}