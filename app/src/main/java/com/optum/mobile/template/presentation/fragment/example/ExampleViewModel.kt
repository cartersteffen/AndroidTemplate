package com.optum.mobile.template.presentation.fragment.example

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.optum.mobile.template.data.api.ExampleApi
import com.optum.mobile.template.data.model.ExampleResponse
import com.optum.mobile.template.presentation.state.DataState
import com.optum.mobile.template.presentation.state.postError
import com.optum.mobile.template.presentation.state.postSuccess
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ExampleViewModel @Inject internal constructor(
    private val exampleApi: ExampleApi
) : ViewModel() {

    private val exampleLiveData = MutableLiveData<DataState<ExampleResponse>>()

    fun fetchExampleData(): MutableLiveData<DataState<ExampleResponse>> {
        val disposable = CompositeDisposable()
        val observable = exampleApi.example()
        disposable.add(observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { data ->
                exampleLiveData.postSuccess(data)
                },
                {
                    exampleLiveData.postError(it.message)
                }
            )
        )
        return exampleLiveData
    }

    fun getExampleData(): MutableLiveData<DataState<ExampleResponse>> {
        return exampleLiveData
    }
}