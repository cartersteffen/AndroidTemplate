package com.optum.mobile.template.core.utils

import android.content.Context
import android.graphics.Typeface

import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.MetricAffectingSpan
import android.text.style.StyleSpan
import android.view.View
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat

fun SpannableString.bold(target: String, ignoreCase: Boolean = false): SpannableString {
    val start = this.indexOf(target, 0, ignoreCase)
    if (start == -1) {
        return this
    }
    val end = start + target.length
    val styleSpan = StyleSpan(Typeface.BOLD)
    setSpan(styleSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

fun SpannableString.font(target: String, @FontRes font: Int, context: Context): SpannableString {
    val start = this.indexOf(target)
    if (start == -1) {
        return this
    }
    val end = start + target.length
    val typeface = Typeface.create(ResourcesCompat.getFont(context, font), Typeface.NORMAL)
    setSpan(FontSpan(typeface), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

/**
 * Give the [target] text a clickable appearance and invoke the given [onClick] listener when tapped.
 */
fun SpannableString.link(target: String, onClick: (View) -> Unit): SpannableString {
    val start = this.indexOf(target)
    if (start == -1) {
        return this
    }
    val end = start + target.length
    setSpan(CustomClickableSpan(onClick), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

class CustomClickableSpan(private val listener: (View) -> Unit) : ClickableSpan() {
    override fun onClick(widget: View) {
        listener(widget)
    }
}

class FontSpan(private val font: Typeface) : MetricAffectingSpan() {
    override fun updateMeasureState(textPaint: TextPaint) = update(textPaint)

    override fun updateDrawState(textPaint: TextPaint) = update(textPaint)

    private fun update(textPaint: TextPaint) {
        textPaint.apply {
            val old = typeface
            val oldStyle = old?.style ?: 0
            val font = Typeface.create(font, oldStyle)
            typeface = font
        }
    }
}
