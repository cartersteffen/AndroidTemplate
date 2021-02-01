package com.optum.mobile.template.data.repository

import android.os.StrictMode
import com.optum.mobile.template.data.api.ConfigApi
import com.optum.mobile.template.data.model.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ConfigRepository(private val configurationApi: ConfigApi) {

    private var config: Config? = null
    var isAsyncActive = false

    // Returns the user config, will make a blocking call to get the user config if it is empty and return null if there was an error
    fun getUserConfig(): Config? {
        if (config != null) {
            return config
        } else {
            try {
                val default = StrictMode.getThreadPolicy()
                val exception = StrictMode.ThreadPolicy.Builder().permitAll().build()
                StrictMode.setThreadPolicy(exception)
                config = configurationApi.getConfiguration().timeout(15, TimeUnit.SECONDS).blockingGet()
                StrictMode.setThreadPolicy(default)
                return config
            } catch (e: Throwable) {
                return null
            }
        }
    }

    fun reloadUserConfig(): Config? {
        try {
            val default = StrictMode.getThreadPolicy()
            val exception = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(exception)
            config = configurationApi.getConfiguration().timeout(15, TimeUnit.SECONDS).blockingGet()
            StrictMode.setThreadPolicy(default)
            return config
        } catch (e: Throwable) {
            return null
        }
    }

    fun fetchUserConfig(listener: ConfigListener) {
        isAsyncActive = true
        val disposable = CompositeDisposable()
        val observable = configurationApi.getConfiguration()
        disposable.add(observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { data ->
                    isAsyncActive = false
                    config = data
                    listener.onGetConfigSuccess(data)
                    disposable.dispose()
                },
                {
                    isAsyncActive = false
                    listener.onGetConfigFailure(it)
                    disposable.dispose()
                }
            )
        )
    }

    fun clear() {
        config = null
    }

    interface ConfigListener {
        fun onGetConfigSuccess(config: Config)
        fun onGetConfigFailure(e: Throwable)
    }
}