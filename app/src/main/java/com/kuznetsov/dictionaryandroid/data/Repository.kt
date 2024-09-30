package com.kuznetsov.dictionaryandroid.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kuznetsov.dictionaryandroid.entity.Word
import com.kuznetsov.dictionaryandroid.entity.Wordbook
import com.kuznetsov.dictionaryandroid.entity.WordbookGroup
import com.kuznetsov.dictionaryandroid.utils.AnswerStatus
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

    private val wordsMap = mutableMapOf<Int, MutableLiveData<List<Word>>>()
    private val wordbooksMap = mutableMapOf<Int, MutableLiveData<List<Wordbook>>>()
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
        wordsMap[wordbookId]?.let {
            return it
        }
        wordsMap[wordbookId] = MutableLiveData<List<Word>>()
        val url = props.getProperty("url") + props.getProperty("words") + wordbookId + ".json"
        Log.i(TAG, "fetchWords url = $url")
        val request = retrofitApi.fetchWords(url)
        request.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.i(TAG, "words response body = ${ response.body().toString() }")
                val json = response.body().toString()
                val gson = Gson()
                if (json.first() == '{') {
                    val type = object: TypeToken<Map<String, Word>>() {}.type
                    wordsMap[wordbookId]?.value =
                        gson.fromJson<Map<String, Word>>(json, type).values
                            .filterNotNull()
                            //.apply { forEach { it.answerStatus = AnswerStatus.UNANSWERED } }
                } else if (json.first() == '[') {
                    val type = object: TypeToken<List<Word>>() {}.type
                    wordsMap[wordbookId]?.value =
                        gson.fromJson<List<Word>>(json, type).filterNotNull()
                            //.apply { forEach { it.answerStatus = AnswerStatus.UNANSWERED } }
                } else {
                    Log.i(TAG, "Error fetchWords: json = $json")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.i(TAG, "fetchWords: ${ t.toString() }")
            }

        })
        return wordsMap[wordbookId]!!
    }

    fun fetchWordbooks(groupId: Int): MutableLiveData<List<Wordbook>> {
        wordbooksMap[groupId]?.let {
            return it
        }
        wordbooksMap[groupId] = MutableLiveData<List<Wordbook>>()
        val url = props.getProperty("url") + props.getProperty("wordbooks") + groupId + ".json"
        Log.i(TAG, "fetchWordbooks url = $url")
        val request = retrofitApi.fetchWordbooks(url)
        request.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.i(TAG, "wordbooks response body = ${ response.body().toString() }")
                val json = response.body().toString()
                val gson = Gson()
                if (json.first() == '{') {
                    val type = object: TypeToken<Map<String, Wordbook>>() {}.type
                    wordbooksMap[groupId]?.value =
                        gson.fromJson<Map<String, Wordbook>>(json, type).values.filterNotNull()
                } else if (json.first() == '[') {
                    val type = object: TypeToken<List<Wordbook>>() {}.type
                    wordbooksMap[groupId]?.value =
                        gson.fromJson<List<Wordbook>>(json, type).filterNotNull()
                } else {
                    Log.i(TAG, "Error fetchWordbooks: json = $json")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.i(TAG, "fetchWordbooks: ${ t.toString() }")
            }

        })
        return wordbooksMap[groupId]!!
    }

    fun fetchWordbookGroups(): MutableLiveData<List<WordbookGroup>>{
        if ((wordbookGroups.value?.size ?: 0) > 0) {
            return wordbookGroups
        }
        val request = retrofitApi.fetchWordbookGroups()
        request.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.i(TAG, "groups response body = ${ response.body().toString() }")

                val json = response.body().toString()
                val gson = Gson()
                if (json.first() == '{') {
                    val type = object : TypeToken<Map<String, WordbookGroup>>() {}.type
                    wordbookGroups.value = gson.fromJson<Map<String, WordbookGroup>>(json, type)
                        .values.toList().filterNotNull()
                } else if (json.first() == '[') {
                    val type = object : TypeToken<List<WordbookGroup>>() {}.type
                    wordbookGroups.value =
                        gson.fromJson<List<WordbookGroup>>(json, type).filterNotNull()
                } else {
                    Log.i(TAG, "Error fetchWordbookGroups: json = $json")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, "fetchWordbookGroups: ${ t.toString() }")
            }
        })
        return wordbookGroups
    }
}