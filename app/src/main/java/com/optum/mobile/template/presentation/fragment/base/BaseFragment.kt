package com.optum.mobile.template.presentation.fragment.base

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.optum.mobile.template.di.HasComponent

abstract class BaseFragment : Fragment() {

    lateinit var updateBaseFragmentListener: UpdateBaseFragmentListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is UpdateBaseFragmentListener) {
            updateBaseFragmentListener = context
        } else {
            throw IllegalStateException("Calling activity must implement UpdateBaseFragmentListener")
        }
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        if (context is UpdateBaseFragmentListener) {
            updateBaseFragmentListener = activity as UpdateBaseFragmentListener
        } else {
            throw IllegalStateException("Calling activity must implement UpdateBaseFragmentListener")
        }
    }

    override fun onResume() {
        super.onResume()
        FirebaseAnalytics.getInstance(requireActivity()).setCurrentScreen(requireActivity(), getPageName(), getPageName())
    }

    abstract fun getPageName(): String

    open fun getBackStackTag(): String? = null

    protected fun <C> getComponent(componentType: Class<C>): C {
        return componentType.cast((activity as HasComponent<*>).getComponent()) as C
    }

    interface UpdateBaseFragmentListener {
        fun addBaseFragment(fragment: BaseFragment, addToBackStack: Boolean = false)
        fun closeFragment()
        fun showToast(text: String, length: Int)
    }
}