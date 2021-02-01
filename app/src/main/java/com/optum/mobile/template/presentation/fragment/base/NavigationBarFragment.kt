package com.optum.mobile.template.presentation.fragment.base

import android.app.Activity
import android.content.Context
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.optum.mobile.template.R
import com.optum.mobile.template.di.HasComponent

abstract class NavigationBarFragment : ToolbarFragment() {

    companion object {
        const val BACKSTACK_BASE = "base_backstack"
    }

    lateinit var navigationBarListener: NavigationBarListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavigationBarListener) {
            navigationBarListener = context
        } else {
            throw IllegalStateException("Calling activity must implement UpdateToolbarListener")
        }
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        if (context is NavigationBarListener) {
            navigationBarListener = activity as NavigationBarListener
        } else {
            throw IllegalStateException("Calling activity must implement UpdateToolbarListener")
        }
    }

    override fun onResume() {
        super.onResume()
        navigationBarListener.updateNavigationContainer(getNavigationFeature())
        navigationBarListener.showNavigationBar(shouldShowNavigationBar())
    }

    open fun shouldShowNavigationBar() = true

    abstract fun getNavigationFeature(): NavigationFeature

    interface NavigationBarListener {
        fun updateNavigationContainer(navigationFeature: NavigationFeature)
        fun addNavigationBarFragment(fragment: NavigationBarFragment, addToBackStack: Boolean = true)
        fun showNavigationBar(shouldShow: Boolean)
    }

    enum class NavigationFeature(private val featureName: String, val menuId: Int) {
        PROFILE("profile", R.id.navigation_account);

        companion object {
            fun getFromFeatureName(name: String): NavigationFeature? {
                for (feature in values()) {
                    if (feature.featureName == name) {
                        return feature
                    }
                }
                return null
            }
        }
    }
}