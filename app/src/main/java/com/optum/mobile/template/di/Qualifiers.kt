package com.optum.mobile.template.di

import kotlin.annotation.AnnotationRetention.RUNTIME

@javax.inject.Qualifier
@kotlin.annotation.Retention(RUNTIME)
annotation class PreAuth

@javax.inject.Qualifier
@kotlin.annotation.Retention(RUNTIME)
annotation class PostAuth

@javax.inject.Qualifier
@kotlin.annotation.Retention(RUNTIME)
annotation class Example