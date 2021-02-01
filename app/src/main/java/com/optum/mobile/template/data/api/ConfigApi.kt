package com.optum.mobile.template.data.api

import com.optum.mobile.template.data.model.Config
import io.reactivex.Single
import retrofit2.http.GET

interface ConfigApi {

    @GET("")
    fun getConfiguration(): Single<Config>
}