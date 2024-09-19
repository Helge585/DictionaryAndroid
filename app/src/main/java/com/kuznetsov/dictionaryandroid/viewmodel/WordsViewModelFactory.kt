package com.kuznetsov.dictionaryandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WordsViewModelFactory(private val wordbookId: Int): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordsViewModel::class.java)) {
            return WordsViewModel(wordbookId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}