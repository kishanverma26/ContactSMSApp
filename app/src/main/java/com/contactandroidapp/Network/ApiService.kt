package com.contactandroidapp.Network

import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("{phone_number}/{otp}")
    fun getUsers(@Path("phone_number") phone_number:String, @Path("otp") otp:String): Call<JSONObject>

}