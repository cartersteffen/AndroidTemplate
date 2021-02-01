package com.optum.mobile.template.di.module

import com.optum.mobile.template.BuildConfig
import com.optum.mobile.template.di.Example
import com.optum.mobile.template.di.PostAuth
import com.optum.mobile.template.di.PreAuth
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val preAuth = "preauth/v1.0/"
const val postAuth = "authenticated/v1.0/"

@Module(includes = [HttpClientModule::class, GsonConverterModule::class])
class RetrofitModule {

    @Provides
    @PreAuth
    fun provideRetrofit(
        @PreAuth okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.baseUrl + preAuth)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @PostAuth
    fun providePostAuthRetrofit(
        @PostAuth okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.baseUrl + postAuth)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Example
    fun provideExampleRetrofit(
        @PreAuth okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/")
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }
}
