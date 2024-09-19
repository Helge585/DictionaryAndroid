package com.kuznetsov.dictionaryandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TestsViewModelFactory(private val wordbookId: Int): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TestsViewModel::class.java)) {
            return TestsViewModel(wordbookId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}