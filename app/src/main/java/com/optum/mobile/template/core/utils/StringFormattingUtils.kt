package com.optum.mobile.template.core.utils

import android.content.Context

import com.optum.mobile.template.R
import java.util.Locale

class StringFormattingUtils {
    companion object {
        fun getDollarString(context: Context, amount: Number?): String {
            return getDollarString(context, amount, "")
        }

        fun getDollarString(context: Context, amount: Number?, blankStr: String): String {
            var str = blankStr
            if (amount != null) {
                val amountStr = "%.2f".format(amount.toFloat())
                str = String.format(Locale.US, context.getString(R.string.dollar_amount), amountStr)
            }
            return str
        }

        fun getSpaceDelineatedStrings(context: Context, firstValue: String?, secondValue: String?): String {
            return getSpaceDelineatedStrings(context, firstValue, secondValue, "")
        }

        fun getSpaceDelineatedStrings(
            context: Context,
            firstValue: String?,
            secondValue: String?,
            blankStr: String
        ): String {
            var first = blankStr
            var second = blankStr
            if (firstValue != null) {
                first = firstValue
            }
            if (secondValue != null) {
                second = secondValue
            }
            return String.format(Locale.US, context.getString(R.string.space_delineated_values), first, second)
        }
    }
}
fun String.removeExcessWhiteSpace(): String {
    return this.replace("\\s+".toRegex(), " ")
}

/**
 * Helper function to trick talk-back into reading out zip codes as distinct numerals, rather than a single number
 * in the 10k-99k range.
 * @return This string if it's not a 5-digit number, otherwise a join of each character with spaces.
 */
fun String.zipContentDescription(): String {
    if (this.length != 5) return this
    if (this.toCharArray().any { !it.isDigit() }) return this

    return this.toCharArray().joinToString(" ")
}
