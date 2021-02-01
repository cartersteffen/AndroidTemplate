package com.optum.mobile.template.presentation.activity

import android.os.Bundle
import com.optum.mobile.template.R
import com.optum.mobile.template.di.HasComponent
import com.optum.mobile.template.di.component.DaggerExampleComponent
import com.optum.mobile.template.di.component.ExampleComponent
import com.optum.mobile.template.presentation.activity.base.BaseActivity
import com.optum.mobile.template.presentation.fragment.example.ExampleFragment

class ExampleActivity : BaseActivity(), HasComponent<ExampleComponent> {

    private lateinit var exampleComponent: ExampleComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        getComponent()

        addBaseFragment(ExampleFragment())
    }

    override fun getFragmentContainerId() = R.id.fragment_pane

    override fun getComponent(): ExampleComponent {
        if (!::exampleComponent.isInitialized) {
            exampleComponent = DaggerExampleComponent.builder().applicationComponent(getApplicationComponent()).build()
            exampleComponent.inject(this)
        }
        return exampleComponent
    }
}
