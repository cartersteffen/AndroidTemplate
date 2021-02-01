package com.optum.mobile.template.core.utils

import android.app.Activity
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

public fun Activity.createAlertDialog(
    title: String?,
    message: String,
    positiveButtonText: String,
    positiveClickListener: DialogInterface.OnClickListener?,
    negativeButtonText: String,
    negativeClickListener: DialogInterface.OnClickListener?
): AlertDialog {

    val alert = AlertDialog.Builder(this)
    if (title.isNullOrEmpty()) {
        alert.setTitle("")
    } else {
        alert.setTitle(title)
    }
    alert.setMessage(message)
    alert.setCancelable(false)
    if (positiveClickListener != null) {
        alert.setPositiveButton(positiveButtonText, positiveClickListener)
    }

    if (negativeClickListener != null) {
        alert.setNegativeButton(negativeButtonText, negativeClickListener)
    }

    return alert.create()
}

public fun Activity.createAlertDialog(
    title: String?,
    message: String,
    positiveButtonText: String,
    positiveClickListener: DialogInterface.OnClickListener?
): AlertDialog {

    val alert = AlertDialog.Builder(this)
    if (title.isNullOrEmpty()) {
        alert.setTitle("")
    } else {
        alert.setTitle(title)
    }
    alert.setMessage(message)
    alert.setCancelable(false)
    if (positiveClickListener != null) {
        alert.setPositiveButton(positiveButtonText, positiveClickListener)
    }

    return alert.create()
}
