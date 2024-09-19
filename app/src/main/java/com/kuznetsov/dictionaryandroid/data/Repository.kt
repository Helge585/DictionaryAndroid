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

    private val words = MutableLiveData<List<Word>>()
    private val wordbooks = MutableLiveData<List<Wordbook>>()
    private val wordbookGroups = MutableLiveData<List<WordbookGroup>>()
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

    fun fetchWords(wordbookId: Int): MutableLiveData<List<Word>> {
        if ((words.value?.size ?: 0) > 0) {
            return words
        }
        val url = props.getProperty("url") + props.getProperty("words") + wordbookId + ".json"
        val request = retrofitApi.fetchWords(url)
        request.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val json = response.body()
                val gson = Gson()
                val type = object: TypeToken<Map<String, Word>>() {}.type
                val map = gson.fromJson<Map<String, Word>>(json, type)
                val bufWords = mutableListOf<Word>()
                for (word in map.values) {
                    Log.i(TAG, word.toString())
                    bufWords.add(word)
                }
                words.value = bufWords
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.i(TAG, "fetchWords: ${ t.toString() }")
            }

        })
        return words
    }

    fun fetchWordbooks(groupId: Int): MutableLiveData<List<Wordbook>> {
        if ((wordbooks.value?.size ?: 0) > 0) {
            return wordbooks
        }
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
                Log.i(TAG, "fetchWordbooks: ${ t.toString() }")
            }

        })
        return wordbooks
    }

    fun fetchWordbookGroups(): MutableLiveData<List<WordbookGroup>>{
        if ((wordbookGroups.value?.size ?: 0) > 0) {
            return wordbookGroups
        }
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
                Log.e(TAG, "fetchWordbookGroups: ${ t.toString() }")
            }
        })
        return wordbookGroups
    }
}