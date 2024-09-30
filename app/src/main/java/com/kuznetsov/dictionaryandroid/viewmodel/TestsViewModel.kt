package com.kuznetsov.dictionaryandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.kuznetsov.dictionaryandroid.data.Repository
import com.kuznetsov.dictionaryandroid.entity.Word
import com.kuznetsov.dictionaryandroid.utils.AnswerStatus

class TestsViewModel(private val wordbookId: Int): ViewModel() {
    private var _words = Repository.fetchWords(wordbookId)

    val words: LiveData<List<Word>> = _words.switchMap { words ->
        MutableLiveData<List<Word>>(
            words.toMutableList().apply {
                forEach { word ->
                    word.answerStatus = AnswerStatus.UNANSWERED
                }
                shuffle()
            }
        )
    }
}