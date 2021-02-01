package com.optum.mobile.template.core

import android.content.Context
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    // TODO UPDATE THESE PARAMETERS TO SUPPORT YOUR APP
    private val context: Context
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        // TODO UPDATE THIS BODY FOR YOUR APP
        return response.request().newBuilder().build()
    }
}
