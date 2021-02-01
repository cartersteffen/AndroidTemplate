package com.optum.mobile.template.presentation

import android.app.Application
import com.adobe.mobile.Config

import com.squareup.leakcanary.LeakCanary
import timber.log.Timber
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import com.crashlytics.android.core.CrashlyticsCore
import com.optum.mobile.cleanarchitecturehelper.logging.TimberLog
import com.optum.mobile.template.BuildConfig
import com.optum.mobile.template.core.datastore.SharedPreferenceDataStore
import com.optum.mobile.template.core.logging.CrashlyticsTree
import com.optum.mobile.template.di.component.ApplicationComponent
import com.optum.mobile.template.di.component.DaggerApplicationComponent
import com.optum.mobile.template.di.module.AppModule
import javax.inject.Inject

class App : Application() {

    lateinit var applicationComponent: ApplicationComponent private set

    @Inject
    lateinit var sharedPreferenceDataStore: SharedPreferenceDataStore

    override fun onCreate() {

        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }

        val core = CrashlyticsCore.Builder()
            .disabled(BuildConfig.DEBUG)
            .build()

        Fabric.with(this, Crashlytics.Builder().core(core).build())

        Config.setContext(this.applicationContext)

        LeakCanary.install(this)

        initDagger()

        if (BuildConfig.DEBUG) {
            Timber.plant(TimberLog())
        }

        Timber.plant(CrashlyticsTree())
    }

    private fun initDagger() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }
}
