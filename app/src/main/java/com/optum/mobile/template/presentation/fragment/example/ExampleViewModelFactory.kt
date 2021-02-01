package com.optum.mobile.template.presentation.fragment.example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.optum.mobile.template.data.api.ExampleApi
import javax.inject.Inject

class ExampleViewModelFactory @Inject constructor(
    private val exampleApi: ExampleApi
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExampleViewModel::class.java)) {
            return ExampleViewModel(exampleApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class =/= ExampleViewModel")
    }
}