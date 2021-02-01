package com.optum.mobile.template.di.component

import com.optum.mobile.template.di.PerFragment
import com.optum.mobile.template.di.module.ExampleModule
import com.optum.mobile.template.presentation.activity.ExampleActivity
import com.optum.mobile.template.presentation.fragment.example.ExampleFragment
import dagger.Component

@PerFragment
@Component(dependencies = [ApplicationComponent::class], modules = [ExampleModule::class])
interface ExampleComponent {
    fun inject(activity: ExampleActivity)
    fun inject(fragment: ExampleFragment)
}