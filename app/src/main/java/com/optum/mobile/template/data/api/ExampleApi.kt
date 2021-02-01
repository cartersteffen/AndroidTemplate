package com.optum.mobile.template.data.api

import com.optum.mobile.template.data.model.ExampleResponse
import io.reactivex.Single
import retrofit2.http.GET

interface ExampleApi {

    @GET("spudmonk3y/demo/fragment")
    fun example(): Single<ExampleResponse>
}
