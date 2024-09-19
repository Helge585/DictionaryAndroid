package com.kuznetsov.dictionaryandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kuznetsov.dictionaryandroid.data.Repository
import com.kuznetsov.dictionaryandroid.entity.WordbookGroup

class WordbookGroupsViewModel: ViewModel() {
    private var _wordbookGroups = Repository.fetchWordbookGroups()
    val wordbookGroups : LiveData<List<WordbookGroup>>
        get() = _wordbookGroups

}