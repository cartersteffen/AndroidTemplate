package com.optum.mobile.template.di.module

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.converter.gson.GsonConverterFactory

@Module
class GsonConverterModule {

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setLenient()
        return GsonConverterFactory.create(gsonBuilder.create())
    }
}
