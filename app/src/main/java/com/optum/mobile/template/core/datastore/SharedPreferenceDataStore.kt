package com.optum.mobile.template.core.datastore

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.WorkerThread
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SharedPreferenceDataStore @Inject constructor(context: Context) {

    private val prefs = context.applicationContext.getSharedPreferences(PREFS_NAME,
        Context.MODE_PRIVATE
    )

    var isLoggedIn by BooleanPreference(prefs, PREF_IS_LOGGED_IN, false)

    companion object {
        const val PREFS_NAME = "motion"
        const val PREF_IS_LOGGED_IN = "pref_is_logged_in"
    }
}

class BooleanPreference(
    private val preferences: SharedPreferences,
    private val name: String,
    private val defaultValue: Boolean
) : ReadWriteProperty<Any, Boolean> {

    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
        return preferences.getBoolean(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
        preferences.edit().putBoolean(name, value).apply()
    }
}

class StringPreference(
    private val preferences: SharedPreferences,
    private val name: String,
    private val defaultValue: String?
) : ReadWriteProperty<Any, String?> {

    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): String? {
        return preferences.getString(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) {
        preferences.edit().putString(name, value).apply()
    }
}
