package com.example.cleanapp.di

import com.google.android.gms.location.LocationRequest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LocationModule {

    @Provides
    fun provideLocationRequest(): LocationRequest = LocationRequest.create()
}