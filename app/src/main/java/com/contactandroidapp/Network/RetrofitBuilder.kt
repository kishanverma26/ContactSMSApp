package com.contactandroidapp.Network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitBuilder {
    //but to factor api-key
    private const val api_key = "bb82f1ba-0130-11ed-9c12-0200cd936042"
    private const val BASE_URL = "https://2factor.in/API/V1/${api_key}/SMS/"

    val client = OkHttpClient().newBuilder().build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val lastFmService = retrofit.create(ApiService::class.java)
}