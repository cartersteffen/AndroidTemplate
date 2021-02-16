package com.optum.mobile.template.presentation.activity

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.optum.mobile.template.R
import com.optum.mobile.template.core.utils.gone
import com.optum.mobile.template.core.utils.visible
import com.optum.mobile.template.di.HasComponent
import com.optum.mobile.template.di.component.ExampleComponent
import com.optum.mobile.template.di.component.NavigationBarComponent
import com.optum.mobile.template.presentation.activity.base.BaseActivity
import com.optum.mobile.template.presentation.fragment.base.NavigationBarFragment
import com.optum.mobile.template.presentation.fragment.base.ToolbarFragment
import com.optum.mobile.template.presentation.fragment.example.ExampleFragment
import kotlinx.android.synthetic.main.activity_navigation_bar.*

class NavigationBarActivity : BaseActivity(), HasComponent<NavigationBarComponent>,
        NavigationBarFragment.NavigationBarListener, ToolbarFragment.UpdateToolbarListener {

    private lateinit var exampleComponent: ExampleComponent
    private lateinit var navigationBarComponent: NavigationBarComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_bar)
        addNavigationBarFragment(ExampleFragment(), false)
    }

    override fun updateNavigationContainer(navigationFeature: NavigationBarFragment.NavigationFeature) {
        bottom_navigation.menu.findItem(navigationFeature.menuId).isChecked = true
    }

    override fun addNavigationBarFragment(fragment: NavigationBarFragment, addToBackStack: Boolean) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(getFragmentContainerId(), fragment)
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getBackStackTag())
        }
    }

    override fun showNavigationBar(shouldShow: Boolean) {
        if (shouldShow) {
            bottom_navigation.visible()
        } else {
            bottom_navigation.gone()
        }
    }

    override fun getFragmentContainerId() = R.id.frame_container

    override fun updateToolbar(shouldShowToolbar: Boolean, toolbar: Toolbar?, shouldShowHomeButton: Boolean, shouldShowHomeAsUp: Boolean) {
        setSupportActionBar(toolbar)
        if (shouldShowToolbar) {
            supportActionBar?.show()
            supportActionBar?.setHomeButtonEnabled(shouldShowHomeButton)
            supportActionBar?.setDisplayHomeAsUpEnabled(shouldShowHomeAsUp)
        } else {
            supportActionBar?.hide()
        }
    }

    override fun getComponent(): NavigationBarComponent {
        return navigationBarComponent
    }
}
