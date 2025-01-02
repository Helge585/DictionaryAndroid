package com.kuznetsov.dictionaryandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.kuznetsov.dictionaryandroid.data.Repository
import com.kuznetsov.dictionaryandroid.entity.Word
import com.kuznetsov.dictionaryandroid.utils.AnswerStatus
import com.kuznetsov.dictionaryandroid.utils.WordType
import java.lang.Integer.min
import java.util.Random

class TestsViewModel(private val wordbookId: Int): ViewModel() {
    private var _words = Repository.fetchWords(wordbookId)
    private val randomNumbersGenerator = Random()

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

    fun generateWordsForGuessingTest(word: Word, guessingWordType: WordType,
                                     wordCount: Int = 3): List<String> {
        val randomWords = mutableListOf<String>()
        if (guessingWordType == WordType.FOREIGN) {
            randomWords.add(word.russianWord)
        } else if (guessingWordType == WordType.RUSSIAN) {
            randomWords.add(word.foreignWord)
        }
        if (wordCount <= 0) {
            return randomWords
        }
        words.value?.let {
            val realWordCount = min(wordCount, it.size)
            var i = 0
            while (i < realWordCount) {
                val addedWord = it.random().run {
                    if (guessingWordType == WordType.FOREIGN) {
                        russianWord
                    } else {
                        foreignWord
                    }
                }
                if (!randomWords.contains(addedWord)) {
                    randomWords.add(addedWord)
                    ++i
                }
            }
        }
        randomWords.shuffle()
        return randomWords
    }
}