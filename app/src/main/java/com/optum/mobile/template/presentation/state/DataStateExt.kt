package com.optum.mobile.template.presentation.state

import android.os.Looper
import androidx.lifecycle.MutableLiveData

// Posts the data with the success state to the live data asynchronously, use this when you are not sure if you are on the main thread
fun <T> MutableLiveData<DataState<T>>.postSuccess(data: T? = null) =
    postValue(DataState(DataState.Status.SUCCESS, data, null))

// Sets the data with the success state to the live data synchronously, only use this method if you are sure you are on the main thread
fun <T> MutableLiveData<DataState<T>>.setSuccess(data: T? = null) {
    if (Looper.getMainLooper().thread == Thread.currentThread()) {
        value = DataState(DataState.Status.SUCCESS, data, null)
    } else {
        postSuccess(data)
    }
}

// Posts the live data to loading state asynchronously, use this when you are not sure if you are on the main thread
fun <T> MutableLiveData<DataState<T>>.postLoading() =
    postValue(DataState(DataState.Status.LOADING, value?.data, null))

// Sets the live data to loading state synchronously, only use this method if you are sure you are on the main thread
fun <T> MutableLiveData<DataState<T>>.setLoading() {
    if (Looper.getMainLooper().thread == Thread.currentThread()) {
        value = DataState(DataState.Status.LOADING, value?.data, null)
    } else {
        postLoading()
    }
}

// Posts the message and the error state to the live data asynchronously, use this when you are not sure if you are on the main thread
fun <T> MutableLiveData<DataState<T>>.postError(message: String? = null) =
    postValue(DataState(DataState.Status.ERROR, value?.data, message))

// Sets the message and the error state to the live data synchronously, only use this method if you are sure you are on the main thread
fun <T> MutableLiveData<DataState<T>>.setError(message: String? = null) {
    if (Looper.getMainLooper().thread == Thread.currentThread()) {
        value = DataState(DataState.Status.ERROR, value?.data, message)
    } else {
        postError(message)
    }
}