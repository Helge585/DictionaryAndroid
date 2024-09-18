package com.kuznetsov.dictionaryandroid.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kuznetsov.dictionaryandroid.entity.Word
import com.kuznetsov.dictionaryandroid.entity.Wordbook
import com.kuznetsov.dictionaryandroid.entity.WordbookGroup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.Properties

private const val TAG = "REPOSITORY"
object Repository {

    private lateinit var retrofit: Retrofit
    private lateinit var retrofitApi: RetrofitApi
    private lateinit var props: Properties

    val wordbookGroups = MutableLiveData<List<WordbookGroup>>()
    val wordbooks = MutableLiveData<List<Wordbook>>()
    val words = MutableLiveData<List<Word>>()

    fun initialize(context: Context) {
        props = Properties()
        context.resources.assets.open("firebase.properties").use {
            props.load(it)
        }
        retrofit = Retrofit.Builder()
            .baseUrl(props.getProperty("url"))
            //.addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        retrofitApi = retrofit.create(RetrofitApi::class.java)
    }

    fun downloadWords(wordbookId: Int) {
        val url = props.getProperty("url") + props.getProperty("words") + wordbookId + ".json"
        val request = retrofitApi.fetchWords(url)
        request.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val json = response.body()
                val gson = Gson()
                val type = object: TypeToken<Map<String, Word>>() {}.type
                val map = gson.fromJson<Map<String, Word>>(json, type)
                val wl = mutableListOf<Word>()
                for (word in map.values) {
                    Log.i(TAG, word.toString())
                    wl.add(word)
                }
                words.value = wl
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.i(TAG, t.toString())
            }

        })
    }

    fun downloadWordbooks(groupId: Int) {
        val url = props.getProperty("url") + props.getProperty("wordbooks") + groupId + ".json"
        val request = retrofitApi.fetchWordbooks(url)
        request.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val json = response.body()
                val gson = Gson()
                val type = object: TypeToken<Map<String, Wordbook>>() {}.type
                val map = gson.fromJson<Map<String, Wordbook>>(json, type)
                val wl = mutableListOf<Wordbook>()
                for (wordbook in map.values) {
                    Log.i(TAG, wordbook.toString())
                    wl.add(wordbook)
                }
                wordbooks.value = wl
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.i(TAG, t.toString())
            }

        })
    }

    fun downloadWordbookGroups() {
        val request = retrofitApi.fetchWordbookGroups()
        request.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val json = response.body().toString()
                val gson = Gson()
                val type = object : TypeToken<Map<String, WordbookGroup>>() {}.type
                val map = gson.fromJson<Map<String, WordbookGroup>>(json, type)
                val wl = mutableListOf<WordbookGroup>()
                for (wordbookGroup in map.values) {
                    wl.add(wordbookGroup)
                }
                wordbookGroups.value = wl
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                val wl = mutableListOf<WordbookGroup>()
                wl.add(WordbookGroup(-999, "ERRRORRR"))
                wordbookGroups.value = wl
            }
        })
    }
}