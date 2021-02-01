package com.optum.mobile.template.core.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)

inline fun <reified T> Gson.fromJson(json: com.google.gson.stream.JsonReader) =
    this.fromJson<T>(json, object : TypeToken<T>() {}.type)
