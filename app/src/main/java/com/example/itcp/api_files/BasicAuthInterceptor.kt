package com.example.itcp.api_files

import android.util.Base64
import android.util.Log
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthInterceptor(username : String, password : String) : Interceptor {
    private val credentials = Credentials.basic(username, password)
    private val auth = "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.DEFAULT)

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .header("Authorization", credentials)
            .build()
        Log.d("HELP ME", auth)
        return chain.proceed(authenticatedRequest)
    }
}