package com.kuznetsov.dictionaryandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kuznetsov.dictionaryandroid.data.Repository
import com.kuznetsov.dictionaryandroid.entity.Wordbook
import com.kuznetsov.dictionaryandroid.entity.WordbookGroup

class WordbooksViewModel(val wordbookGroupId: Int): ViewModel() {
    private var _wordbooks = Repository.fetchWordbooks(wordbookGroupId)
    val wordbooks: LiveData<List<Wordbook>> get() = _wordbooks

}