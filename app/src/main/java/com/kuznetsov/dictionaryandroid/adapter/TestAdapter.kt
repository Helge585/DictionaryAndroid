package com.kuznetsov.dictionaryandroid.adapter

import android.graphics.Color
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.kuznetsov.dictionaryandroid.R
import com.kuznetsov.dictionaryandroid.databinding.TestItemBinding
import com.kuznetsov.dictionaryandroid.entity.Word
import com.kuznetsov.dictionaryandroid.utils.AnswerStatus

private const val TAG = "TestAdapter"

class TestAdapter(
    val testMode: String,
    val step: (Int) -> Unit,
    val setAnswer: (Boolean, Int) -> Unit
): ListAdapter<Word, TestAdapter.TestHolder>(WordDiffItemCallback()) {

    // words are prepared in view model.
    // And view models are needed to be renamed to see what fragment they belong to

//    override fun onCurrentListChanged(
//        previousList: MutableList<Word>,
//        currentList: MutableList<Word>
//    ) {
//        super.onCurrentListChanged(previousList, currentList)
//        currentList.forEach { word ->
//            word?.let {
//                word.answerStatus = AnswerStatus.UNANSWERED
//            }
//        }
//    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestHolder {
        return TestHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: TestHolder, position: Int) {

        holder.bind(getItem(position), testMode, position, step, setAnswer)
    }

    class TestHolder(private val binding: TestItemBinding): ViewHolder(binding.root) {

        fun bind(word: Word, testMode: String, position: Int, step: (Int) -> Unit,
                 setAnswer: (Boolean, Int) -> Unit) {

            binding.word = word

            setStyle(testMode, word, true)

            binding.nextButton.setOnClickListener {
                step(position + 1)
            }
            binding.prevButton.setOnClickListener {
                step(position - 1)
            }
            binding.showAnswerButton.setOnClickListener {
                showWordsAndExamples(word)
            }

            binding.russianWord.setOnKeyListener { view, i, keyEvent ->
                if (i == KeyEvent.KEYCODE_ENTER && keyEvent.action == KeyEvent.ACTION_DOWN) {
                    setAnswer(checkAnswer(testMode, word), 1)
                }
                return@setOnKeyListener true
            }

            binding.foreignWord.setOnKeyListener { view, i, keyEvent ->
                if (i == KeyEvent.KEYCODE_ENTER) {
                    setAnswer(checkAnswer(testMode, word), 1)
                }
                return@setOnKeyListener true
            }
        }

        private fun setStyle(testMode: String, word: Word, isClearTypingFields: Boolean = false) {
            when (word.answerStatus) {
                AnswerStatus.UNANSWERED -> {
                    setUnansweredStyle(testMode, word, isClearTypingFields)
                }
                AnswerStatus.RIGHT -> {
                    setRightAnswerStyle(testMode, word)
                }
                AnswerStatus.WRONG -> {
                    setWrongAnswerStyle(testMode, word, isClearTypingFields)
                }
            }
        }

        private fun setUnansweredStyle(testMode: String, word: Word, isClearTypingFields: Boolean) {
            when (testMode) {
                "Write Russian" -> {
                    binding.russianWord.isEnabled = true
                    if (isClearTypingFields) {
                        binding.russianWord.setText("")
                    }


                    binding.foreignWord.isEnabled = false
                    binding.foreignWord.setTextColor(Color.BLACK)
                    binding.foreignWord.setText(word.foreignWord)
                }
                "Write foreign" -> {
                    binding.foreignWord.isEnabled = true
                    if (isClearTypingFields) {
                        binding.foreignWord.setText("")
                    }


                    binding.russianWord.isEnabled = false
                    binding.russianWord.setTextColor(Color.BLACK)
                    binding.russianWord.setText(word.russianWord)
                    if (isClearTypingFields) {
                        binding.foreignWord.setText("")
                    }
                }
            }

            binding.russianWord.setBackgroundResource(R.drawable.test_edittext_background)
            binding.foreignWord.setBackgroundResource(R.drawable.test_edittext_background)

            binding.russianExample.setText("")
            binding.foreignExample.setText("")
        }

        private fun setRightAnswerStyle(testMode: String, word: Word) {
            showWordsAndExamples(word)

            binding.russianWord.isEnabled = false
            binding.russianWord.setTextColor(Color.BLACK)
            binding.russianWord.setBackgroundResource(R.drawable.test_item_right_answer)

            binding.foreignWord.isEnabled = false
            binding.foreignWord.setTextColor(Color.BLACK)
            binding.foreignWord.setBackgroundResource(R.drawable.test_item_right_answer)
        }

        private fun setWrongAnswerStyle(testMode: String, word: Word, isClearTypingFields: Boolean) {
            when (testMode) {
                "Write Russian" -> {
                    binding.russianWord.isEnabled = true
                    if (isClearTypingFields) {
                        binding.russianWord.setText("")
                    }

                    binding.foreignWord.isEnabled = false
                    binding.foreignWord.setTextColor(Color.BLACK)
                    binding.foreignWord.setText(word.foreignWord)
                }
                "Write foreign" -> {
                    binding.foreignWord.isEnabled = true
                    if (isClearTypingFields) {
                        binding.foreignWord.setText("")
                    }

                    binding.russianWord.isEnabled = false
                    binding.russianWord.setTextColor(Color.BLACK)
                    binding.russianWord.setText(word.russianWord)
                }
            }

            binding.russianWord.setBackgroundResource(R.drawable.test_item_wrong_answer)
            binding.foreignWord.setBackgroundResource(R.drawable.test_item_wrong_answer)

            binding.russianExample.setText("")
            binding.foreignExample.setText("")
        }

        private fun checkAnswer(testMode: String, word: Word): Boolean {
            val isAnswerTrue = when (testMode) {
                "Write Russian" -> {
                    binding.russianWord.text.toString().lowercase().trim() ==
                            word.russianWord.lowercase().trim()
                }
                "Write foreign" -> {
                    binding.foreignWord.text.toString().lowercase().trim() ==
                            word.foreignWord.lowercase().trim()
                }
                else -> { false }
            }
            if (isAnswerTrue) {
                word.answerStatus = AnswerStatus.RIGHT
                setStyle(testMode, word)
            } else {
                word.answerStatus = AnswerStatus.WRONG
                setStyle(testMode, word)
            }
            return isAnswerTrue
        }

        private fun showWordsAndExamples(word: Word) {
            binding.russianExample.text = word.russianExample
            binding.foreignExample.text = word.foreignExample
            binding.russianWord.setText(word.russianWord)
            binding.foreignWord.setText(word.foreignWord)
        }

        companion object {
            fun inflateFrom(parent: ViewGroup): TestHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                return TestHolder(TestItemBinding.inflate(layoutInflater, parent, false))
            }
        }
    }
}