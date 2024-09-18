package com.kuznetsov.dictionaryandroid

import com.kuznetsov.dictionaryandroid.entity.Wordbook

interface WordsOpenListener {
    fun onWordsFragmentOpen(wordbook: Wordbook)
}