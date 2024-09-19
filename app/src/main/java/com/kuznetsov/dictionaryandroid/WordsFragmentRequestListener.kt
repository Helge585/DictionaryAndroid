package com.kuznetsov.dictionaryandroid

import com.kuznetsov.dictionaryandroid.entity.Wordbook

interface WordsFragmentRequestListener {
    fun onWordsFragmentRequest(wordbook: Wordbook)
}