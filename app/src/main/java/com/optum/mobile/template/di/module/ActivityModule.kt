package com.optum.mobile.template.di.module

import android.app.Activity
import com.optum.mobile.template.di.PerActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule constructor(private val activity: Activity) {

    @Provides
    @PerActivity
    fun provideActivity(): Activity = this.activity
}
