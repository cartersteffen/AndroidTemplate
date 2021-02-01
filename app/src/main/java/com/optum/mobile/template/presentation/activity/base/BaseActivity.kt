package com.optum.mobile.template.presentation.activity.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.optum.mobile.template.core.analytics.Analytics
import com.optum.mobile.template.di.component.ApplicationComponent
import com.optum.mobile.template.di.module.ActivityModule
import com.optum.mobile.template.presentation.App
import com.optum.mobile.template.presentation.fragment.base.BaseFragment
import timber.log.Timber

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity(), BaseFragment.UpdateBaseFragmentListener {

    /**
     * Supply a fragment container ID to host the internal fragment stack.
     */
    open fun getFragmentContainerId(): Int = View.NO_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getApplicationComponent().inject(this)
    }

    protected fun getApplicationComponent(): ApplicationComponent {
        return ((application as App)).applicationComponent
    }

    protected fun getActivityModule(): ActivityModule {
        return ActivityModule(this)
    }

    override fun addBaseFragment(fragment: BaseFragment, addToBackStack: Boolean) {
        if (getFragmentContainerId() != View.NO_ID) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(getFragmentContainerId(), fragment)
            if (addToBackStack) {
                transaction.addToBackStack(null)
            }
            transaction.commit()
        } else {
            Timber.e("Attempted to add fragment to an activity that does not have a fragment container defined")
        }
    }

    override fun closeFragment() {
        onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        Analytics.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        Analytics.onPause()
    }

    override fun showToast(text: String, length: Int) {
        runOnUiThread {
            Toast.makeText(this, text, length).show()
        }
    }
}