package com.kuznetsov.dictionaryandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WordbooksViewModelFactory(private val wordbookGroupId: Int): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordbooksViewModel::class.java)) {
            return WordbooksViewModel(wordbookGroupId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}