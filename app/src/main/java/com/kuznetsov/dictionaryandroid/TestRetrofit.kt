package com.kuznetsov.dictionaryandroid

import retrofit2.Call
import retrofit2.http.GET

interface TestRetrofit {

    @GET("/root.json")
    fun fetchWordbooks(): Call<String>
}