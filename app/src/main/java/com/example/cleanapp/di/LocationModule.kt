package com.example.cleanapp.di

import com.example.cleanapp.LocationSettings
import com.google.android.gms.location.LocationRequest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class LocationModule {

    @Provides
    fun provideLocationRequest() = LocationRequest.create()
}