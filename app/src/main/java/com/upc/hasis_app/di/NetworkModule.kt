package com.upc.hasis_app.di

import com.upc.hasis_app.data.api.ApiRest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    //private const val API_URL = "https://catfact.ninja/"
    private const val API_URL = "http://192.168.0.8:8080/api/v1/"

    @Provides
    @Singleton
    fun provideApiRest(): ApiRest {
        return Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRest::class.java)
    }
}