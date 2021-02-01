package com.optum.mobile.template.core.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

fun Bitmap.resizeAndCompressToByteArray(reqWidth: Int, reqHeight: Int): ByteArray {
    val imageStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 70, imageStream)
    val compressedByteArray = imageStream.toByteArray()
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeByteArray(compressedByteArray, 0, compressedByteArray.size, options)

    // Calculate inSampleSize
    options.inSampleSize = BitmapExt.calculateInSampleSize(options, reqWidth, reqHeight)

    // Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false
    val frontImageStream = ByteArrayOutputStream()
    BitmapFactory.decodeByteArray(compressedByteArray, 0, compressedByteArray.size, options)
        .compress(Bitmap.CompressFormat.JPEG, 70, frontImageStream)
    return frontImageStream.toByteArray()
}

class BitmapExt {
    companion object {
        fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {

            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1

            if (height > reqHeight || width > reqWidth) {
                val halfHeight = height / 2
                val halfWidth = width / 2

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                    inSampleSize *= 2
                }
            }

            return inSampleSize
        }
    }
}
