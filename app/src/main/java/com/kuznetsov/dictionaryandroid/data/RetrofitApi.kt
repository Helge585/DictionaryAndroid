package com.kuznetsov.dictionaryandroid.data

import com.kuznetsov.dictionaryandroid.entity.WordbookGroup
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface RetrofitApi {

    @GET("/WordbookGroups.json")
    fun fetchWordbookGroups(): Call<String>

    @GET
    fun fetchWordbooks(@Url url: String): Call<String>

    @GET
    fun fetchWords(@Url url: String): Call<String>
}