package com.optum.mobile.template.di.component

import com.optum.mobile.template.di.PerFragment
import com.optum.mobile.template.di.module.RetrofitModule
import com.optum.mobile.template.presentation.activity.NavigationBarActivity
import dagger.Component

@PerFragment
@Component(dependencies = [ApplicationComponent::class], modules = [RetrofitModule::class])
interface NavigationBarComponent {
    fun inject(activity: NavigationBarActivity)
}