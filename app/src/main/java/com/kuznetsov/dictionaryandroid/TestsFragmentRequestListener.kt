package com.kuznetsov.dictionaryandroid

import com.kuznetsov.dictionaryandroid.entity.Wordbook
import com.kuznetsov.dictionaryandroid.utils.TestMode

interface TestsFragmentRequestListener {
    fun onTestFragmentRequest(wordbook: Wordbook, testMode: String)
}