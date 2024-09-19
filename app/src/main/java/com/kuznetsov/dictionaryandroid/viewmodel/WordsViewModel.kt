package com.kuznetsov.dictionaryandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kuznetsov.dictionaryandroid.data.Repository
import com.kuznetsov.dictionaryandroid.entity.Word

class WordsViewModel(private val wordbookId: Int): ViewModel() {
    private var _words = Repository.fetchWords(wordbookId)
    val words: LiveData<List<Word>> get() = _words
}