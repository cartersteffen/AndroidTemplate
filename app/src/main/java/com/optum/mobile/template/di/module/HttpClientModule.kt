package com.optum.mobile.template.di.module

import com.optum.mobile.template.di.PostAuth
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import com.optum.mobile.template.core.TokenAuthenticator
import com.optum.mobile.template.di.PreAuth
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.util.concurrent.TimeUnit

@Module()
class HttpClientModule {

    @Provides
    @PostAuth
    fun providesPostAuthHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()

        builder.readTimeout(60, TimeUnit.SECONDS)
        builder.connectTimeout(60, TimeUnit.SECONDS)

        try {
            builder.addInterceptor { chain ->
                val request: Request = chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + "OAuth Token From CACHE") // TODO UPDATE THIS TO USE YOUR CACHED VALUE
                    .build()
                chain.proceed(request)
            }
        } catch (e: Exception) {
            Timber.e("PreAuthOkHttpClient Error while setting headers $e")
        }
        builder.authenticator(tokenAuthenticator)
        builder.addInterceptor(httpLoggingInterceptor)
        return builder.build()
    }

    @Provides
    @PreAuth
    fun providesPreAuthOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor):
            OkHttpClient {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()

        builder.readTimeout(60, TimeUnit.SECONDS)
        builder.connectTimeout(60, TimeUnit.SECONDS)

        try {
            builder.addInterceptor { chain ->
                val request: Request = chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(request)
            }
        } catch (e: Exception) {
            Timber.e("PreAuthOkHttpClient Error while setting headers $e")
        }
        builder.addInterceptor(httpLoggingInterceptor)
        return builder.build()
    }

    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }
}
