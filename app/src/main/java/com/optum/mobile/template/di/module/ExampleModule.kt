package com.optum.mobile.template.di.module

import com.optum.mobile.template.data.api.ExampleApi
import com.optum.mobile.template.di.Example
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = [RetrofitModule::class])
class ExampleModule {

    @Provides
    fun provideExampleApi(@Example retrofit: Retrofit): ExampleApi {
        return retrofit.create(ExampleApi::class.java)
    }
}