package com.kuznetsov.dictionaryandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kuznetsov.dictionaryandroid.data.Repository
import com.kuznetsov.dictionaryandroid.entity.Wordbook
import com.kuznetsov.dictionaryandroid.entity.WordbookGroup
import com.kuznetsov.dictionaryandroid.fragments.TestsFragment
import com.kuznetsov.dictionaryandroid.fragments.WordbookGroupFragment
import com.kuznetsov.dictionaryandroid.fragments.WordbooksFragment
import com.kuznetsov.dictionaryandroid.fragments.WordsFragment

private const val TAG = "MainActivity"
class MainActivity: AppCompatActivity(),
                    WordbooksFragmentRequestListener,
                    WordsFragmentRequestListener,
                    TestsFragmentRequestListener
{

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

    override fun onWordbooksFragmentRequest(wordbookGroup: WordbookGroup) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.host, WordbooksFragment.newInstance(wordbookGroup))
            .addToBackStack(null)
            .commit()
    }

    override fun onWordsFragmentRequest(wordbook: Wordbook) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.host, WordsFragment.newInstance(wordbook.id))
            .addToBackStack(null)
            .commit()
    }

    override fun onTestFragmentRequest(wordbook: Wordbook) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.host, TestsFragment.newInstance(wordbook.id))
            .addToBackStack(null)
            .commit()
    }
}