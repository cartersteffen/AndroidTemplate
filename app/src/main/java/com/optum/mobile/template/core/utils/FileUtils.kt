package com.optum.mobile.template.core.utils

import android.content.Context
import timber.log.Timber
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.charset.Charset

    fun writeToFile(context: Context, data: String, file: File) {
    val fileOutputStream = FileOutputStream(file)

    try {

        fileOutputStream.write(data.toByteArray(Charset.defaultCharset()))
    } catch (e: Exception) {
        Timber.e("Exception - File write failed: $e")
    } finally {
        fileOutputStream.close()
    }
}

    fun readFromFile(context: Context, file: File): String {

    val inputStream = FileInputStream(file)
    val length = file.length().toInt()
    val bytes = ByteArray(length)

    try {
        inputStream.read(bytes)
    } catch (e: Exception) {
        Timber.e("Exception - File read failed: $e")
    } finally {
        inputStream.close()
    }

    return String(bytes)
}
