package com.kuznetsov.dictionaryandroid

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.sql.DriverManager
import java.util.Properties


class MainActivity : AppCompatActivity() {

    lateinit var hello_world: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hello_world = findViewById(R.id.hello_world)

        val props = Properties()
        resources.assets.open("firebase.properties").use {
            props.load(it)
        }
        val retrofit = Retrofit.Builder()
            .baseUrl(props.getProperty("url"))
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        val testRetrofit = retrofit.create(TestRetrofit::class.java)

        val request = testRetrofit.fetchWordbooks()

        request.enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                hello_world.text = response.body()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                hello_world.text = "Fail"
            }

        })
    }
}