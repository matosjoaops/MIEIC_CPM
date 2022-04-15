package com.example.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface ServerService {
    @GET("hello")
    fun getHello() : Call<String>;
}