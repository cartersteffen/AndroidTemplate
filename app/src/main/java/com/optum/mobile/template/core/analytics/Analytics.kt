package com.optum.mobile.template.core.analytics

import android.app.Activity
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

object Analytics {

    private var firebaseAnalytics: FirebaseAnalytics? = null

    fun trackPageState(stateName: String, additionalStateData: HashMap<String, String> = HashMap<String, String>()) {
    }

    fun trackTap(actionName: String, additionalContextData: HashMap<String, String>) {

        if (firebaseAnalytics != null) {
            val bundle = Bundle()
            for (entry in additionalContextData) {
                bundle.putString(entry.key.split(".").last(), entry.value)
            }
            firebaseAnalytics?.logEvent(actionName, bundle)
        }
    }

    fun onResume(activity: Activity) {
    }

    fun onPause() {
    }
}