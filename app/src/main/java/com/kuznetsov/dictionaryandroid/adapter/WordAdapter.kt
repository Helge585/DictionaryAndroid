package com.kuznetsov.dictionaryandroid.adapter

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kuznetsov.dictionaryandroid.databinding.WordItemBinding
import com.kuznetsov.dictionaryandroid.entity.Word
import com.kuznetsov.dictionaryandroid.entity.Wordbook

class WordAdapter(
    ): ListAdapter<Word, WordAdapter.WordHolder>(WordDiffItemCallback())
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordHolder {
        return WordHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: WordHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class WordHolder(private val binding: WordItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(word: Word) {
            binding.word = word
        }

        companion object {
            fun inflateFrom(parent: ViewGroup): WordHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                return WordHolder(WordItemBinding.inflate(layoutInflater, parent, false))
            }
        }
    }
}