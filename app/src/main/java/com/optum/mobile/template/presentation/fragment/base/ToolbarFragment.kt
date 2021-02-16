package com.optum.mobile.template.presentation.fragment.base

import android.app.Activity
import android.content.Context
import androidx.appcompat.widget.Toolbar

abstract class ToolbarFragment : BaseFragment() {

    lateinit var updateToolbarListener: UpdateToolbarListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is UpdateToolbarListener) {
            updateToolbarListener = context
        } else {
            throw IllegalStateException("Calling activity must implement UpdateToolbarListener")
        }
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        if (context is UpdateToolbarListener) {
            updateToolbarListener = activity as UpdateToolbarListener
        } else {
            throw IllegalStateException("Calling activity must implement UpdateToolbarListener")
        }
    }

    override fun onResume() {
        super.onResume()
        updateToolbarListener.updateToolbar(shouldShowToolbar(), getToolbar(), shouldShowHomeButton(), shouldShowHomeAsUp())
    }

    override fun onDestroy() {
        super.onDestroy()
        updateToolbarListener.updateToolbar(false, null, false, false)
    }

    open fun shouldShowToolbar() = false

    open fun getToolbar(): Toolbar? = null

    open fun shouldShowHomeButton() = false

    open fun shouldShowHomeAsUp() = false

    interface UpdateToolbarListener {
        fun updateToolbar(
            shouldShowToolbar: Boolean,
            toolbar: Toolbar?,
            shouldShowHomeButton: Boolean,
            shouldShowHomeAsUp: Boolean
        )
    }
}