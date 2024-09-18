package com.kuznetsov.dictionaryandroid

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.kuznetsov.dictionaryandroid.data.Repository
import com.kuznetsov.dictionaryandroid.entity.Wordbook
import com.kuznetsov.dictionaryandroid.entity.WordbookGroup
import com.kuznetsov.dictionaryandroid.fragments.WordbookGroupFragment
import com.kuznetsov.dictionaryandroid.fragments.WordbooksFragment
import com.kuznetsov.dictionaryandroid.fragments.WordsFragment
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.StringBuilder
import java.util.Properties

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity(), WordbookOpenListener, WordsOpenListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Repository.initialize(applicationContext)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.host, WordbookGroupFragment())
                .commit()
        }
    }

    override fun onWordbookFragmentOpen(wordbookGroup: WordbookGroup) {
        //Log.i(TAG, wordbookGroup.toString())
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.host, WordbooksFragment.newInstance(wordbookGroup))
            .addToBackStack(null)
            .commit()
    }

    override fun onWordsFragmentOpen(wordbook: Wordbook) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.host, WordsFragment.newInstance(wordbook.id))
            .addToBackStack(null)
            .commit()
    }
}