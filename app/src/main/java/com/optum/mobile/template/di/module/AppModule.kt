package com.optum.mobile.template.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import com.google.gson.GsonBuilder
import com.optum.mobile.cleanarchitecturehelper.executors.DefaultSchedulers
import com.optum.mobile.cleanarchitecturehelper.executors.JobExecutor
import com.optum.mobile.cleanarchitecturehelper.executors.PostExecutionThread
import com.optum.mobile.cleanarchitecturehelper.executors.RxSchedulers
import com.optum.mobile.cleanarchitecturehelper.executors.ThreadExecutor
import com.optum.mobile.cleanarchitecturehelper.executors.UIThread
import com.optum.mobile.template.core.datastore.SharedPreferenceDataStore
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

@Module
class AppModule(val app: Application) {

    @Provides
    @Singleton
    fun provideApp(): Application = app

    @Provides
    @Singleton
    fun provideContext(): Context = this.app

    @Provides
    @Singleton
    fun provideSharedPreferenceDataStore(context: Context):
            SharedPreferenceDataStore =
        SharedPreferenceDataStore(context)

    @Provides
    @Singleton
    fun provideRxScheduler(): RxSchedulers = DefaultSchedulers()

    @Provides
    @Singleton
    fun provideThreadExecutor(jobExecutor: JobExecutor):
            ThreadExecutor = jobExecutor

    @Provides
    @Singleton
    fun provideScheduler(threadExecutor: ThreadExecutor): Scheduler = Schedulers.from(threadExecutor)

    @Provides
    @Singleton
    fun providePostExecutionThread(uiThread: UIThread):
            PostExecutionThread = uiThread

    @Provides
    @Singleton
    fun provideSerializer(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setLenient()
        return gsonBuilder.create()
    }
}
