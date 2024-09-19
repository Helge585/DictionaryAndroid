package com.kuznetsov.dictionaryandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.kuznetsov.dictionaryandroid.databinding.TestItemBinding
import com.kuznetsov.dictionaryandroid.entity.Word

class TestAdapter: ListAdapter<Word, TestAdapter.TestHolder>(WordDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestHolder {
        return TestHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: TestHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TestHolder(private val binding: TestItemBinding): ViewHolder(binding.root) {

        fun bind(word: Word) {
            binding.word = word
        }

        companion object {
            fun inflateFrom(parent: ViewGroup): TestHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                return TestHolder(TestItemBinding.inflate(layoutInflater, parent, false))
            }
        }
    }
}