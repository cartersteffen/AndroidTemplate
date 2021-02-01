package com.optum.mobile.template.core.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

fun Activity.checkPermission(permission: String): Boolean {
    return (ActivityCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED)
}

fun Fragment.checkPermission(permission: String): Boolean {
    return (ActivityCompat.checkSelfPermission(
        this.requireActivity(),
        permission
    ) == PackageManager.PERMISSION_GRANTED)
}

fun Context.checkPermission(permission: String): Boolean {
    return (ActivityCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED)
}
