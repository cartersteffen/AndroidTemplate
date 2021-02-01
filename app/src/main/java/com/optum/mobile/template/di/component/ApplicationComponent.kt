package com.optum.mobile.template.di.component

import android.app.Activity
import android.app.Service
import android.content.Context
import com.google.gson.Gson
import com.optum.mobile.cleanarchitecturehelper.executors.PostExecutionThread
import com.optum.mobile.cleanarchitecturehelper.executors.RxSchedulers
import com.optum.mobile.cleanarchitecturehelper.executors.ThreadExecutor
import com.optum.mobile.template.core.datastore.SharedPreferenceDataStore
import com.optum.mobile.template.di.module.AppModule

import dagger.Component
import io.reactivex.Scheduler
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface ApplicationComponent {
    fun inject(activity: Activity)
    fun inject(service: Service)

    // Exposed to sub-graphs
    val context: Context
    val threadExecutor: ThreadExecutor
    val postExecutionThread: PostExecutionThread
    val serializer: Gson
    val scheduler: Scheduler
    val sharedPreferenceDataStore: SharedPreferenceDataStore
    val rxSchedulers: RxSchedulers
}