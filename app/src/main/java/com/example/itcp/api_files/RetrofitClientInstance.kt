package com.example.itcp.api_files

import com.example.itcp.api_files.BasicAuthInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClientInstance(username : String, password: String) {
    private val BASE_URL = "http://10.0.2.2/smshs_api/"
    val client = OkHttpClient.Builder()
        .addInterceptor(BasicAuthInterceptor(username, password))
        .build()

    private val gson = GsonBuilder().setLenient().create()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    fun getRetrofitInstance(): Retrofit {
        return retrofit
    }
}