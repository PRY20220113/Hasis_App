package com.upc.hasis_app.di

import com.upc.hasis_app.data.api.ApiRest
import com.upc.hasis_app.data.local.preferences.LocalPreferenceDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {


}